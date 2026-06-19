# Flujos del Sistema — RAO MOTOS

Base de datos: PostgreSQL.  
Todas las operaciones se disparan por comandos enviados por email (Subject line).

---

## CU1 — Gestión de Usuarios

### Tablas involucradas

```
usuario  (base)
  ├── cliente      (PK = usuario.id, FK CASCADE)
  └── propietario  (PK = usuario.id, FK CASCADE)
proveedor  (independiente, sin cuenta de usuario)
```

### Flujo

1. **Registro de Cliente**
   ```
   CREATEUSUARIO[nombre, email, pass, rol=CLIENTE, telefono, direccion]
   ```
   - Se inserta en `usuario` con `rol = 'CLIENTE'`.
   - Luego se inserta en `cliente` con el mismo `id`, más `nit_ci` y `tipo_cliente` (dato que llega por separado vía `UPDATECLIENTE`).

2. **Registro de Propietario**
   ```
   CREATEUSUARIO[nombre, email, pass, rol=PROPIETARIO, telefono, direccion]
   ```
   - Se inserta en `usuario` con `rol = 'PROPIETARIO'`.
   - Luego se inserta en `propietario` con el mismo `id` y `nivel_acceso = 'TOTAL'`.

3. **Registro de Proveedor** (entidad comercial, sin login)
   ```
   CREARPROVEEDOR[razonSocial, contacto, telefono]
   ```
   - Solo inserta en `proveedor`.

4. **Actualizar datos de usuario** — modifica `usuario` (nombre, email, tel, dir, activo).
5. **Actualizar datos de cliente** (`UPDATECLIENTE`) — modifica `cliente.nit_ci` y `cliente.tipo_cliente`.
6. **Eliminación** — borra `usuario`; `ON DELETE CASCADE` limpia `cliente`/`propietario`.

### Herencia física

`cliente` y `propietario` comparten el PK con `usuario`. No pueden existir sin su padre.  
`proveedor` es tabla separada sin herencia.

---

## CU2 — Gestión de Productos

### Tablas

```
producto (única)
```

### Flujo

1. **Crear producto**
   ```
   CREATEPRODUCTO[codigo, nombre, marca, modelo, descripcion, precio]
   ```
   - Se inserta en `producto` con `activo = TRUE`.
   - Se crea automáticamente un registro en `inventario` para este producto con `stock_actual = 0`.

2. **Actualizar** — modifica campos del producto (precio, activo, etc.).
3. **Eliminación lógica** — `activo = FALSE`. No se borra físicamente por las FK `ON DELETE RESTRICT` desde `detalle_compra`, `detalle_venta`, `detalle_pedido`, `inventario`.

---

## CU3 — Gestión de Compras

### Tablas

```
compra  ──1:N── detalle_compra
```

### Flujo

1. **Crear compra**
   ```
   CREARCOMPRA[proveedorId, monto]
   ```
   - Se inserta en `compra` con `estado = 'PENDIENTE'`.
   - El usuario debe luego agregar los detalles con productos, cantidades y precios.

2. **Recibir compra** (confirmar)
   - Se cambia `compra.estado = 'RECIBIDA'`.
   - **Dispara automáticamente** la creación de movimientos de inventario:
     - Por cada `detalle_compra`, se inserta un `movimiento_inventario` con `tipo_movimiento = 'INGRESO'` y la cantidad comprada.
     - Se incrementa `inventario.stock_actual`.

3. **Anular compra**
   ```
   ANULARCOMPRA[id]
   ```
   - `compra.estado = 'ANULADA'`. No deshace inventario (diseño actual).

### Relaciones

- `detalle_compra.compra_id` → `compra.id` **CASCADE**: si se elimina la compra, se borran sus detalles.
- `detalle_compra.producto_id` → `producto.id` **RESTRICT**: no se puede borrar un producto con compras asociadas.

---

## CU4 — Gestión de Pedidos

### Tablas

```
pedido  ──1:N── detalle_pedido
```

### Flujo

1. **Crear pedido**
   ```
   CREARPEDIDO[clienteId]
   ```
   - Se inserta en `pedido` con `estado = 'SOLICITADO'`.
   - Luego se agregan los productos solicitados en `detalle_pedido`.

2. **Despachar pedido**
   ```
   DESPACHARPEDIDO[id]
   ```
   - `pedido.estado` pasa por `'EN_PROCESO'` → `'DESPACHADO'`.
   - Al despachar se genera un **egreso de inventario**:
     - Por cada `detalle_pedido`, se inserta `movimiento_inventario` con `tipo_movimiento = 'EGRESO'`.
     - Se decrementa `inventario.stock_actual`.

3. **Anular pedido**
   ```
   ANULARPEDIDO[id]
   ```
   - `pedido.estado = 'ANULADO'`. No revierte inventario.

### Estados

```
SOLICITADO → EN_PROCESO → DESPACHADO  (adelante)
SOLICITADO → ANULADO                   (cancelación)
```

---

## CU5 — Gestión de Inventario

### Tablas

```
inventario  ──1:N── movimiento_inventario
```

### Técnicas

Cada `inventario` tiene dos atributos de configuración:

| Columna | Valores | Significado |
|---|---|---|
| `tecnica_inventario` | `PERMANENTE` / `PERIODICO` | Control continuo vs. conteo periódico |
| `tecnica_costo` | `PEPS` / `UEPS` / `PROMEDIO` | Método de valoración de inventario |

### Flujo

1. **REGISTRARINGRESO[productoId, cantidad, descripcion]**
   - Inserta `movimiento_inventario` con `tipo_movimiento = 'INGRESO'`.
   - Incrementa `inventario.stock_actual`.

2. **REGISTRAREGRESO[productoId, cantidad, descripcion]**
   - Inserta `movimiento_inventario` con `tipo_movimiento = 'EGRESO'`.
   - Decrementa `inventario.stock_actual` (valida que `stock_actual >= 0`).

3. **Movimientos automáticos**
   - Compra recibida → `INGRESO` automático.
   - Pedido despachado → `EGRESO` automático.
   - Venta completada → `EGRESO` automático.

4. **Auditoría** — `movimiento_inventario` guarda toda la historia: cuándo, cuánto, por qué y quién.

### CHECK

- `stock_actual >= 0` (no se permite inventario negativo).

---

## CU6 — Gestión de Ventas

### Tablas

```
venta  ──1:N── detalle_venta
  │
  └──1:1── credito  ──1:N── pago_cuota  (solo si tipo_venta = 'CREDITO')
```

### Flujo General

```
CREARVENTA_CONTADO[clienteId, fecha, monto, metodoPago]
  → venta.tipo_venta = 'CONTADO'
  → venta.estado = 'COMPLETADA'
  → EGRESO automático en inventario (por cada detalle)
  → Fin

CREARVENTA_CREDITO[clienteId, fecha, monto, cuotas, interes, metodoPago]
  → venta.tipo_venta = 'CREDITO'
  → venta.estado = 'PENDIENTE'
  → Se crea credito 1:1 con saldo_pendiente = monto + intereses
  → Se generan automáticamente N pago_cuota (mínimo 2)
  → EGRESO automático en inventario
  → Cada pago de cuota reduce saldo_pendiente
  → Cuando saldo_pendiente = 0 → credito.estado = 'PAGADO'
```

### Métodos de pago

| Método | Aplica a |
|---|---|
| `EFECTIVO` | Contado y Crédito |
| `QR` | Contado y Crédito |
| `TARJETA` | Contado y Crédito |

### Venta al Contado

1. Se crea `venta` con `tipo_venta = 'CONTADO'`, `estado = 'COMPLETADA'`.
2. Se insertan los productos en `detalle_venta`.
3. Se descuenta stock: por cada línea se genera `movimiento_inventario` (`EGRESO`) y se resta de `inventario.stock_actual`.

### Venta al Crédito

1. Se crea `venta` con `tipo_venta = 'CREDITO'`, `estado = 'PENDIENTE'`.
2. Se insertan productos en `detalle_venta`.
3. Se descuenta stock (igual que contado, el producto sale físicamente).
4. Se crea `credito` con:
   - `numero_cuotas >= 2`
   - `tasa_interes` (porcentaje)
   - `saldo_pendiente = monto_total + (monto_total * tasa_interes / 100)`
   - `estado = 'VIGENTE'`
5. Se generan N filas en `pago_cuota`, una por cuota:
   - `monto_cuota = saldo_pendiente / numero_cuotas`
   - `fecha_vencimiento` calculada (cada 30 días desde la fecha de venta)
   - `estado = 'PENDIENTE'`

### Estados de venta

```
CONTADO → COMPLETADA
CREDITO → PENDIENTE → COMPLETADA (cuando credito se paga)
Ambos   → ANULADA
```

---

## CU7 — Gestión de Pagos (Créditos, Moras, Interés)

### Tablas

```
credito  ──1:N── pago_cuota
```

### Flujo de pago de cuota

```
PAGARCUOTA[creditoId, numeroCuota, monto]
```

1. Se busca la cuota en `pago_cuota` por `credito_id` y `numero_cuota`.
2. Si `fecha_pago > fecha_vencimiento`:
   - Se calcula la **mora** como un porcentaje extra sobre `monto_cuota`.
   - `pago_cuota.mora` se actualiza con ese valor.
3. Se registra `fecha_pago = CURRENT_DATE`.
4. `pago_cuota.estado = 'PAGADO'`.
5. Se reduce `credito.saldo_pendiente -= monto_cuota`.
6. Si `saldo_pendiente <= 0`:
   - `credito.estado = 'PAGADO'`.
   - `venta.estado = 'COMPLETADA'`.
7. Si hay cuotas con `fecha_vencimiento < CURRENT_DATE` y no están pagadas:
   - Esa cuota se marca como `'VENCIDO'`.
   - Si al menos una cuota está vencida, `credito.estado = 'MOROSO'`.

### Estados de credito

```
VIGENTE  (al día, cuotas al corriente)
MOROSO   (una o más cuotas vencidas)
PAGADO   (saldo_pendiente = 0)
```

### Estados de pago_cuota

```
PENDIENTE → PAGADO  (pagada a tiempo o con mora)
PENDIENTE → VENCIDO (pasó la fecha de vencimiento sin pago)
```

### Cálculo de mora

```
mora = monto_cuota × (días_de_retraso / 30) × (tasa_interes / 100)
```

(Multiplicador de ejemplo; el cálculo real depende de la lógica de negocio implementada en el servicio.)

---

## CU8 — Reportes y Estadísticas

### Reportes disponibles

| Comando | Descripción | Tablas consultadas |
|---|---|---|
| `REPORT_VENTAS_POR_MES[yyyy-MM]` | Ventas totales, cantidad, método de pago, tipo de venta agrupado por día del mes indicado | `venta`, `detalle_venta`, `cliente` |
| `REPORT_VENTAS_POR_CLIENTE[clienteId]` | Historial completo de compras de un cliente, incluyendo créditos y estado de pagos | `venta`, `detalle_venta`, `credito`, `pago_cuota` |
| `REPORT_MORAS_PENDIENTES` | Todos los créditos en estado MOROSO detallando cuotas vencidas, mora calculada y datos del cliente | `credito`, `pago_cuota`, `venta`, `cliente`, `usuario` |
| `VERINVENTARIO[*\|id]` | Stock actual de todos o un producto específico | `inventario`, `producto` |
| `LISTARCREDITOS[*]` | Todos los créditos con su estado actual | `credito`, `venta`, `cliente` |
| `VERCUOTAS[creditoId]` | Plan de pagos de un crédito específico | `pago_cuota` |

---

## Resumen de flujo completo (extremo a extremo)

```
1. PROVEEDOR vende a RAO MOTOS
   CREARCOMPRA → detalle_compra → RECIBIDA → INGRESO inventario ↑ stock

2. RAO MOTOS compra a PROVEEDOR (inversa, pero conceptualmente igual)

3. CLIENTE solicita productos
   CREARPEDIDO → detalle_pedido → DESPACHADO → EGRESO inventario ↓ stock

   O compra directamente:
   CREARVENTA_CONTADO → detalle_venta → COMPLETADA → EGRESO inventario ↓ stock
   CREARVENTA_CREDITO → detalle_venta → credito → pago_cuota → EGRESO inventario ↓ stock

4. CLIENTE paga crédito
   PAGARCUOTA → reduce saldo_pendiente → si todo pagado → CREDITO PAGADO

5. Si no paga → mora calculada → cuota VENCIDO → credito MOROSO
```

### Mapa de restricciones referenciales

| FK | Desde | Hacia | ON DELETE |
|---|---|---|---|
| `cliente.id` | cliente | usuario | CASCADE |
| `propietario.id` | propietario | usuario | CASCADE |
| `inventario.producto_id` | inventario | producto | RESTRICT |
| `movimiento_inventario.inventario_id` | movimiento_inventario | inventario | RESTRICT |
| `compra.proveedor_id` | compra | proveedor | RESTRICT |
| `detalle_compra.compra_id` | detalle_compra | compra | CASCADE |
| `detalle_compra.producto_id` | detalle_compra | producto | RESTRICT |
| `pedido.cliente_id` | pedido | cliente | RESTRICT |
| `detalle_pedido.pedido_id` | detalle_pedido | pedido | CASCADE |
| `detalle_pedido.producto_id` | detalle_pedido | producto | RESTRICT |
| `venta.cliente_id` | venta | cliente | RESTRICT |
| `detalle_venta.venta_id` | detalle_venta | venta | CASCADE |
| `detalle_venta.producto_id` | detalle_venta | producto | RESTRICT |
| `credito.venta_id` | credito | venta | RESTRICT |
| `pago_cuota.credito_id` | pago_cuota | credito | RESTRICT |
