# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Email-driven business system for "RAO MOTOS" (motorbike shop). Users interact entirely by sending emails to `grupo02sa@tecnoweb.org.bo` with commands in the **Subject** line. The server polls POP3 every 10 seconds, processes commands, queries PostgreSQL, and replies via SMTP.

Requires access to `mail.tecnoweb.org.bo` (university network or VPN) for both email and database.

## Commands

```bash
# Compile
mvn clean compile

# Run interactive menu (BD test / CRUD test / start email service)
mvn exec:java

# Start email polling service directly (skip menu)
mvn exec:java -Dexec.args="email"

# Create all DB tables from database_schema.sql
mvn clean compile exec:java -Dexec.mainClass="org.mailgrupo02.CrearTablas"

# Build fat JAR with all dependencies
mvn package
```

## Setup

Copy `.env.example` to `.env` in the project root. The actual credentials (including passwords) are in `Credentials.txt` (not committed). `EnvLoader` reads `.env` from the current working directory; falls back to `System.getenv()`.

Required `.env` keys: `SMTP_HOST`, `SMTP_PORT`, `EMAIL_USER`, `EMAIL_PASSWORD`, `EMAIL_ADDRESS`, `DB_URL`, `DB_USER`, `DB_PASSWORD`.

## Architecture

### Layer naming conventions
- `*M.java` — Model/DAO: raw JDBC `PreparedStatement` queries, one class per DB table
- `*N.java` — DTO (data transfer object) for a business domain
- `*Service.java` — Business logic; all methods return a formatted `String` (the email reply body)
- `*Validator.java` — Input validation helpers used by services

### Request flow
```
ClientePOP (reads emails) 
  → Main.ServicioEmail.procesarCorreo()
    → ComandoEmailNuevo.evaluarYEjecutar(subject)  ← regex dispatch on all commands
      → *Service methods (validate + call *M DAOs)
        → Conexion.conectar() (PostgreSQL JDBC singleton)
  → ClienteSMTP.enviarCorreo(from, subject, response)
```

### Key files
| File | Role |
|---|---|
| `Main.java` | Entry point; interactive menu + `"email"` arg to skip to service |
| `servicioemail/ComandoEmailNuevo.java` | Central command router — all Subject patterns matched with regex here |
| `servicioemail/ClientePOP.java` / `ClienteSMTP.java` | POP3 reader / SMTP sender |
| `sistema/conexion/Conexion.java` | JDBC singleton for PostgreSQL |
| `Database/EnvLoader.java` | Reads `.env` file; used by `ConfigEmailServer` and `Conexion` |
| `database_schema.sql` | Full DDL for all 15 tables |
| `seed_data.sql` | Sample data for development |
| `TestRunner.java` | Full integration test suite — sends real emails via SMTP for every HELP command |

### Domain organization under `sistema/negocio/`
Each subdomain (`usuarios`, `productos`, `ventas`, `compras`, `pedidos`, `inventario`, `pagos`) follows the same structure: DTO (`*N`), service (`*Service`), optional validator (`*Validator`).

### Database schema summary
- **Inheritance pattern**: `usuario` (base) → `cliente` / `proveedor` / `propietario` (shared PK, ON DELETE CASCADE)
- **Sales flow**: `venta` → `detalle_venta` (line items); if `tipo_venta = 'CREDITO'`, creates `credito` (1:1) → `pago_cuota` (N installments auto-generated)
- **Inventory**: `inventario` (one row per product, tracks stock) + `movimiento_inventario` (audit log of every change)

### Presentation layer (`presentacion/email/`)

All `P*.java` files share the same visual design system:

- **`.ok-card`** — gray gradient (`#f8fafc → #e8edf2`), red 5px top border, centered ✓ icon (52px `#b91c1c`), bold uppercase "OPERACIÓN EXITOSA" title, optional red pill `.id-badge` showing the created/affected ID.
- **`.err-card`** — red gradient, ✗ icon, "ERROR EN LA OPERACIÓN".
- **`.lista-hdr` + `.lista-wrap` + `.table-pre`** — dark gray header bar + rounded border wrapping a monospace `<pre>` block for list/table results.
- **`PPagos.java`** uses a blue theme (`#1e3a8a`) instead of red, and adds a `.qr-card` to wrap QR passthrough HTML.
- **`PUsuarios.java` / `PProductos.java`** additionally include `.badge-ok/.badge-edit/.badge-del`, `.dt` (field table), and `.dif` (before/after comparison table) CSS — used by detail cards built in the controllers.

#### Controller detail cards (`presentacion/email/controladores/`)

`UsuarioControlador` and `ProductoControlador` build rich HTML cards directly and pass them to `generarHtml()`. The signal is: if `resultado.startsWith("<div class=\"detalle")` → insert raw HTML without wrapping.

- **CREATE** → `fichaUsuario(u, "create")` / `fichaProducto(p)` — full field table with green `.badge-ok`.
- **UPDATE** → `diffUsuario(antes, despues)` / `diffProducto(antes, despues)` — before/after `.dif` table.
- **UPDATECLIENTE** → `diffCliente(usuario, clienteAntes, clienteNuevo)` — shows nitCi and tipoCliente before/after.
- **DELETE** → `fichaUsuario(u, "delete")` — field table with red `.badge-del`.
- **GET** → `fichaUsuario(u, "get")` — field table with green `.badge-ok`.

#### `ProductoM.crear()` returns `int`
Changed from `String` to `int` — returns the generated primary key. `ProductoService.agregarProducto()` uses this to include `(ID: N)` in the response string.

### Commands (complete list handled by `ComandoEmailNuevo`)

```
HELP
LISTARUSUARIOS[*]          CREATEUSUARIO[nombre,email,pass,rol,telefono,direccion]
GETUSUARIO[id]             UPDATEUSUARIO[id,nombre,email,pass,rol,tel,dir,activo]
DELETEUSUARIO[id]          UPDATECLIENTE[id,nitCi,tipoCliente]

LISTARPRODUCTOS[*]         CREATEPRODUCTO[codigo,nombre,marca,modelo,descripcion,precio]
GETPRODUCTO[id]            UPDATEPRODUCTO[id,codigo,nombre,marca,modelo,desc,precio,activo]
DELETEPRODUCTO[id]

LISTARCOMPRAS[*]           CREARCOMPRA[proveedorId,monto]
GETCOMPRA[id]              ANULARCOMPRA[id]

LISTARPEDIDOS[*]           CREARPEDIDO[clienteId]
GETPEDIDO[id]              DESPACHARPEDIDO[id]
ANULARPEDIDO[id]

LISTARVENTAS[*]            CREARVENTA_CONTADO[clienteId,fecha,monto,metodoPago]
GETVENTA[id]               CREARVENTA_CREDITO[clienteId,fecha,monto,cuotas,interes,metodoPago]
DELETEVENTA[id]

VERINVENTARIO[*|id]        REGISTRARINGRESO[productoId,cantidad,descripcion]
                           REGISTRAREGRESO[productoId,cantidad,descripcion]

LISTARCREDITOS[*]          VERCUOTAS[creditoId]
PAGARCUOTA[creditoId,numeroCuota,monto]

REPORT_VENTAS_POR_MES[yyyy-MM]
REPORT_VENTAS_POR_CLIENTE[clienteId]
REPORT_MORAS_PENDIENTES[*]
```

Date format for ventas: `2026-06-05T10:00:00`. `tipoCliente` values: `REGULAR / FRECUENTE / MAYORISTA`.

### TestRunner

Run with `mvn exec:java -Dexec.mainClass="org.mailgrupo02.TestRunner"` or from the interactive menu (option 4). Prompts for a destination email then fires every command above in sequence, 2 seconds apart. IDs are extracted from responses with `Pattern.compile("\\(ID:\\s*(\\d+)\\)")` to chain dependent operations. Requires university network / VPN.

## Known gaps / pending work

Role-based access control is **not yet implemented** — any sender can run any command. The README contains a detailed plan for adding it (modify `procesarCorreo` → `ComandoEmailNuevo.evaluarYEjecutar` to accept sender email + rol, add `UsuarioService.buscarRolPorEmail()`, define permission matrix).
