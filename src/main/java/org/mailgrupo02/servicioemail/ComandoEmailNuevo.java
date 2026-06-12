package org.mailgrupo02.servicioemail;

import org.mailgrupo02.sistema.modelo.*;
import org.mailgrupo02.sistema.negocio.usuarios.UsuarioService;
import org.mailgrupo02.sistema.negocio.productos.ProductoService;
import org.mailgrupo02.sistema.negocio.ventas.VentaService;
import org.mailgrupo02.sistema.negocio.compras.CompraService;
import org.mailgrupo02.sistema.negocio.pedidos.PedidoService;
import org.mailgrupo02.sistema.negocio.inventario.InventarioService;
import org.mailgrupo02.sistema.negocio.pagos.PagoCuotaService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComandoEmailNuevo {

    private UsuarioService usuarioService;
    private ProductoService productoService;
    private VentaService ventaService;
    private CompraService compraService;
    private PedidoService pedidoService;
    private InventarioService inventarioService;
    private PagoCuotaService pagoCuotaService;
    private EstadisticaM estadisticaM;

    public ComandoEmailNuevo() throws SQLException {
        this.usuarioService = new UsuarioService(new UsuarioM());
        this.productoService = new ProductoService(new ProductoM());
        this.ventaService = new VentaService(new VentaM(), new CreditoM());
        this.compraService = new CompraService();
        this.pedidoService = new PedidoService();
        this.inventarioService = new InventarioService();
        this.pagoCuotaService = new PagoCuotaService();
        this.estadisticaM = new EstadisticaM();
    }

    public String evaluarYEjecutar(String subject) throws SQLException {
        if (subject.equals("HELP")) {
            return obtenerComandosDisponibles();
        }

        String respuestaConsulta;

        Pattern listarPatron = Pattern.compile("^LISTAR(\\w+)\\[\\*\\]$");
        Pattern crearPatron = Pattern.compile("^CREATE(\\w+)\\[(.+)]$");
        Pattern actualizarPatron = Pattern.compile("^UPDATE(\\w+)\\[(.+)]$");
        Pattern eliminarPatron = Pattern.compile("^DELETE(\\w+)\\[(.+)]$");
        Pattern getPatron = Pattern.compile("^GET(\\w+)\\[(\\d+)]$");

        Pattern ventaContadoPatron = Pattern.compile("^CREARVENTA_CONTADO\\[(.+)]$");
        Pattern ventaCreditoPatron = Pattern.compile("^CREARVENTA_CREDITO\\[(.+)]$");
        Pattern registrarPagoPatron = Pattern.compile("^REGISTRARPAGO\\[(.+)]$");
        Pattern crearCompraPatron = Pattern.compile("^CREARCOMPRA\\[(.+)]$");
        Pattern anularCompraPatron = Pattern.compile("^ANULARCOMPRA\\[(\\d+)]$");
        Pattern crearPedidoPatron = Pattern.compile("^CREARPEDIDO\\[(\\d+)]$");
        Pattern despacharPedidoPatron = Pattern.compile("^DESPACHARPEDIDO\\[(\\d+)]$");
        Pattern anularPedidoPatron = Pattern.compile("^ANULARPEDIDO\\[(\\d+)]$");
        Pattern verInventarioPatron = Pattern.compile("^VERINVENTARIO\\[(\\d+)]$");
        Pattern verInventarioAllPatron = Pattern.compile("^VERINVENTARIO\\[\\*\\]$");
        Pattern registrarIngresoPatron = Pattern.compile("^REGISTRARINGRESO\\[(.+)]$");
        Pattern registrarEgresoPatron = Pattern.compile("^REGISTRAREGRESO\\[(.+)]$");
        Pattern listarCreditosPatron = Pattern.compile("^LISTARCREDITOS\\[\\*\\]$");
        Pattern verCuotasPatron = Pattern.compile("^VERCUOTAS\\[(\\d+)]$");
        Pattern pagarCuotaPatron = Pattern.compile("^PAGARCUOTA\\[(.+)]$");

        Pattern reportVentasMesPatron = Pattern.compile("^REPORT_VENTAS_POR_MES\\[(.+)]$");
        Pattern reportVentasClientePatron = Pattern.compile("^REPORT_VENTAS_POR_CLIENTE\\[(\\d+)]$");
        Pattern reportMorasPatron = Pattern.compile("^REPORT_MORAS_PENDIENTES\\[\\*\\]$");

        Matcher matcher;

        if ((matcher = listarPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            respuestaConsulta = ejecutarConsultaListar(entidad);
        } else if ((matcher = crearPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            String parametros = matcher.group(2);
            respuestaConsulta = ejecutarConsultaCrear(entidad, parametros);
        } else if ((matcher = actualizarPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            String parametros = matcher.group(2);
            respuestaConsulta = ejecutarConsultaActualizar(entidad, parametros);
        } else if ((matcher = eliminarPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            String parametros = matcher.group(2);
            respuestaConsulta = ejecutarConsultaEliminar(entidad, parametros);
        } else if ((matcher = getPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            int id = Integer.parseInt(matcher.group(2));
            respuestaConsulta = ejecutarConsultaGet(entidad, id);
        } else if ((matcher = ventaContadoPatron.matcher(subject)).matches()) {
            String parametros = matcher.group(1);
            respuestaConsulta = ejecutarVentaContado(parametros);
        } else if ((matcher = ventaCreditoPatron.matcher(subject)).matches()) {
            String parametros = matcher.group(1);
            respuestaConsulta = ejecutarVentaCredito(parametros);
        } else if ((matcher = registrarPagoPatron.matcher(subject)).matches()) {
            String parametros = matcher.group(1);
            respuestaConsulta = ejecutarRegistrarPago(parametros);
        } else if ((matcher = crearCompraPatron.matcher(subject)).matches()) {
            String parametros = matcher.group(1);
            respuestaConsulta = ejecutarCrearCompra(parametros);
        } else if ((matcher = anularCompraPatron.matcher(subject)).matches()) {
            int id = Integer.parseInt(matcher.group(1));
            respuestaConsulta = compraService.anularCompra(id);
        } else if ((matcher = crearPedidoPatron.matcher(subject)).matches()) {
            int clienteId = Integer.parseInt(matcher.group(1));
            respuestaConsulta = pedidoService.crearPedido(clienteId);
        } else if ((matcher = despacharPedidoPatron.matcher(subject)).matches()) {
            int id = Integer.parseInt(matcher.group(1));
            respuestaConsulta = pedidoService.despacharPedido(id);
        } else if ((matcher = anularPedidoPatron.matcher(subject)).matches()) {
            int id = Integer.parseInt(matcher.group(1));
            respuestaConsulta = pedidoService.anularPedido(id);
        } else if ((matcher = verInventarioAllPatron.matcher(subject)).matches()) {
            respuestaConsulta = inventarioService.verInventario();
        } else if ((matcher = verInventarioPatron.matcher(subject)).matches()) {
            int productoId = Integer.parseInt(matcher.group(1));
            respuestaConsulta = inventarioService.verInventarioPorProducto(productoId);
        } else if ((matcher = registrarIngresoPatron.matcher(subject)).matches()) {
            String parametros = matcher.group(1);
            respuestaConsulta = ejecutarRegistrarIngreso(parametros);
        } else if ((matcher = registrarEgresoPatron.matcher(subject)).matches()) {
            String parametros = matcher.group(1);
            respuestaConsulta = ejecutarRegistrarEgreso(parametros);
        } else if ((matcher = listarCreditosPatron.matcher(subject)).matches()) {
            respuestaConsulta = pagoCuotaService.listarCreditos();
        } else if ((matcher = verCuotasPatron.matcher(subject)).matches()) {
            int creditoId = Integer.parseInt(matcher.group(1));
            respuestaConsulta = pagoCuotaService.verCuotas(creditoId);
        } else if ((matcher = pagarCuotaPatron.matcher(subject)).matches()) {
            String parametros = matcher.group(1);
            respuestaConsulta = ejecutarPagarCuota(parametros);
        } else if ((matcher = reportVentasMesPatron.matcher(subject)).matches()) {
            String mes = matcher.group(1);
            respuestaConsulta = ejecutarReporteVentasMes(mes);
        } else if ((matcher = reportVentasClientePatron.matcher(subject)).matches()) {
            int idCliente = Integer.parseInt(matcher.group(1));
            respuestaConsulta = ejecutarReporteVentasCliente(idCliente);
        } else if ((matcher = reportMorasPatron.matcher(subject)).matches()) {
            respuestaConsulta = ejecutarReporteMoras();
        } else {
            respuestaConsulta = "Comando no reconocido. Envíe HELP para ver comandos disponibles.";
        }

        return respuestaConsulta;
    }

    private String ejecutarConsultaListar(String entidad) throws SQLException {
        switch (entidad.toUpperCase()) {
            case "USUARIO":
            case "USUARIOS":
                return usuarioService.obtenerUsuarios();
            case "PRODUCTO":
            case "PRODUCTOS":
                return productoService.obtenerProductos();
            case "VENTA":
            case "VENTAS":
                return ventaService.obtenerVentas();
            case "COMPRA":
            case "COMPRAS":
                return compraService.obtenerCompras();
            case "PEDIDO":
            case "PEDIDOS":
                return pedidoService.obtenerPedidos();
            default:
                return "Entidad no reconocida: " + entidad;
        }
    }

    private String ejecutarConsultaCrear(String entidad, String parametros) throws SQLException {
        String[] params = extraerParametros(parametros);

        try {
            switch (entidad.toUpperCase()) {
                case "USUARIO":
                    if (params.length < 6)
                        return "Parámetros insuficientes para crear usuario";
                    return usuarioService.agregarUsuario(
                            params[0], params[1], params[2], params[3], params[4], params[5]);

                case "PRODUCTO":
                    if (params.length < 6)
                        return "Parámetros insuficientes para crear producto";
                    return productoService.agregarProducto(
                            params[0], params[1], params[2], params[3], params[4], Double.parseDouble(params[5]));

                default:
                    return "No se puede crear entidad: " + entidad;
            }
        } catch (Exception e) {
            return "Error al crear " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaActualizar(String entidad, String parametros) throws SQLException {
        String[] params = extraerParametros(parametros);

        try {
            switch (entidad.toUpperCase()) {
                case "USUARIO":
                    if (params.length < 8)
                        return "Parámetros insuficientes para actualizar usuario";
                    return usuarioService.actualizarUsuario(
                            Integer.parseInt(params[0]), params[1], params[2], params[3],
                            params[4], params[5], params[6], Boolean.parseBoolean(params[7]));

                case "PRODUCTO":
                    if (params.length < 8)
                        return "Parámetros insuficientes para actualizar producto";
                    return productoService.actualizarProducto(
                            Integer.parseInt(params[0]), params[1], params[2], params[3],
                            params[4], params[5], Double.parseDouble(params[6]), Boolean.parseBoolean(params[7]));

                default:
                    return "No se puede actualizar entidad: " + entidad;
            }
        } catch (Exception e) {
            return "Error al actualizar " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaEliminar(String entidad, String parametros) throws SQLException {
        String[] params = extraerParametros(parametros);

        try {
            int id = Integer.parseInt(params[0]);

            switch (entidad.toUpperCase()) {
                case "USUARIO":
                    return usuarioService.eliminarUsuario(id);
                case "PRODUCTO":
                    return productoService.eliminarProducto(id);
                case "VENTA":
                    return ventaService.eliminarVenta(id);
                default:
                    return "No se puede eliminar entidad: " + entidad;
            }
        } catch (Exception e) {
            return "Error al eliminar " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaGet(String entidad, int id) throws SQLException {
        try {
            switch (entidad.toUpperCase()) {
                case "USUARIO":
                    return usuarioService.leerUsuario(id).toString();
                case "PRODUCTO":
                    return productoService.leerProducto(id).toString();
                case "VENTA":
                    return ventaService.leerVenta(id).toString();
                case "COMPRA":
                    return compraService.leerCompra(id).toString();
                case "PEDIDO":
                    return pedidoService.leerPedido(id).toString();
                default:
                    return "Entidad no reconocida: " + entidad;
            }
        } catch (Exception e) {
            return "Error al obtener " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarVentaContado(String parametros) throws SQLException {
        try {
            String[] params = extraerParametros(parametros);
            if (params.length < 4)
                return "Parámetros insuficientes para venta al contado";

            int clienteId = Integer.parseInt(params[0]);
            Timestamp fecha = Timestamp.valueOf(params[1]);
            double montoTotal = Double.parseDouble(params[2]);
            String metodoPago = params[3];

            return ventaService.crearVentaContado(clienteId, fecha, montoTotal, metodoPago);
        } catch (Exception e) {
            return "Error al crear venta al contado: " + e.getMessage();
        }
    }

    private String ejecutarVentaCredito(String parametros) throws SQLException {
        try {
            String[] params = extraerParametros(parametros);
            if (params.length < 6)
                return "Parámetros insuficientes para venta a crédito";

            int clienteId = Integer.parseInt(params[0]);
            Timestamp fecha = Timestamp.valueOf(params[1]);
            double montoTotal = Double.parseDouble(params[2]);
            int numeroCuotas = Integer.parseInt(params[3]);
            double tasaInteres = Double.parseDouble(params[4]);
            String metodoPago = params[5];

            return ventaService.crearVentaCredito(clienteId, fecha, montoTotal, numeroCuotas, tasaInteres, metodoPago);
        } catch (Exception e) {
            return "Error al crear venta a crédito: " + e.getMessage();
        }
    }

    private String ejecutarRegistrarPago(String parametros) throws SQLException {
        try {
            String[] params = extraerParametros(parametros);
            if (params.length < 3)
                return "Parámetros insuficientes";

            int creditoId = Integer.parseInt(params[0]);
            int numeroCuota = Integer.parseInt(params[1]);
            double montoCuota = Double.parseDouble(params[2]);

            return pagoCuotaService.registrarPago(creditoId, numeroCuota, montoCuota);
        } catch (Exception e) {
            return "Error al registrar pago: " + e.getMessage();
        }
    }

    private String ejecutarCrearCompra(String parametros) throws SQLException {
        try {
            String[] params = extraerParametros(parametros);
            if (params.length < 2)
                return "Parámetros insuficientes";

            int proveedorId = Integer.parseInt(params[0]);
            double total = Double.parseDouble(params[1]);

            return compraService.crearCompra(proveedorId, total);
        } catch (Exception e) {
            return "Error al crear compra: " + e.getMessage();
        }
    }

    private String ejecutarRegistrarIngreso(String parametros) throws SQLException {
        try {
            String[] params = extraerParametros(parametros);
            if (params.length < 3)
                return "Parámetros insuficientes";

            int productoId = Integer.parseInt(params[0]);
            int cantidad = Integer.parseInt(params[1]);
            String motivo = params[2];

            return inventarioService.registrarIngreso(productoId, cantidad, motivo);
        } catch (Exception e) {
            return "Error al registrar ingreso: " + e.getMessage();
        }
    }

    private String ejecutarRegistrarEgreso(String parametros) throws SQLException {
        try {
            String[] params = extraerParametros(parametros);
            if (params.length < 3)
                return "Parámetros insuficientes";

            int productoId = Integer.parseInt(params[0]);
            int cantidad = Integer.parseInt(params[1]);
            String motivo = params[2];

            return inventarioService.registrarEgreso(productoId, cantidad, motivo);
        } catch (Exception e) {
            return "Error al registrar egreso: " + e.getMessage();
        }
    }

    private String ejecutarPagarCuota(String parametros) throws SQLException {
        try {
            String[] params = extraerParametros(parametros);
            if (params.length < 3)
                return "Parámetros insuficientes";

            int creditoId = Integer.parseInt(params[0]);
            int numeroCuota = Integer.parseInt(params[1]);
            double montoCuota = Double.parseDouble(params[2]);

            return pagoCuotaService.registrarPago(creditoId, numeroCuota, montoCuota);
        } catch (Exception e) {
            return "Error al pagar cuota: " + e.getMessage();
        }
    }

    private String ejecutarReporteVentasMes(String mes) throws SQLException {
        try {
            List<Map<String, Object>> ventas = estadisticaM.obtenerVentasPorMes(mes);
            Map<String, Object> totales = estadisticaM.obtenerTotalesMes(mes);

            StringBuilder sb = new StringBuilder();
            sb.append("=== REPORTE DE VENTAS DEL MES ").append(mes).append(" ===\r\n\r\n");

            String format = "%-5s %-12s %-15s %-20s%n";
            sb.append(String.format(format, "ID", "Fecha", "Total", "Cliente"));
            sb.append("---------------------------------------------------------------------\r\n");

            for (Map<String, Object> venta : ventas) {
                sb.append(String.format(format,
                        venta.get("id_venta"),
                        venta.get("fecha_venta"),
                        String.format("%.2f", venta.get("total")),
                        venta.get("cliente")));
            }

            sb.append("\r\n=== TOTALES ===\r\n");
            sb.append("Total Ventas: ").append(totales.get("total_ventas")).append("\r\n");
            sb.append("Monto Total: ").append(String.format("%.2f", totales.get("monto_total"))).append("\r\n");
            sb.append("Total Contado: ").append(String.format("%.2f", totales.get("total_contado"))).append("\r\n");
            sb.append("Total Crédito: ").append(String.format("%.2f", totales.get("total_credito"))).append("\r\n");

            return sb.toString();
        } catch (Exception e) {
            return "Error al generar reporte: " + e.getMessage();
        }
    }

    private String ejecutarReporteVentasCliente(int idCliente) throws SQLException {
        try {
            List<Map<String, Object>> ventas = estadisticaM.obtenerVentasPorCliente(idCliente);

            StringBuilder sb = new StringBuilder();
            sb.append("=== REPORTE DE VENTAS POR CLIENTE (ID: ").append(idCliente).append(") ===\r\n\r\n");

            String format = "%-5s %-12s %-15s %-15s %-15s%n";
            sb.append(String.format(format, "ID", "Fecha", "Total", "Tipo Venta", "Estado"));
            sb.append("---------------------------------------------------------------------\r\n");

            double totalGeneral = 0;
            for (Map<String, Object> venta : ventas) {
                sb.append(String.format(format,
                        venta.get("id_venta"),
                        venta.get("fecha_venta"),
                        String.format("%.2f", venta.get("total")),
                        venta.get("tipo_venta"),
                        venta.get("estado")));
                totalGeneral += (Double) venta.get("total");
            }

            sb.append("\r\nTotal General: ").append(String.format("%.2f", totalGeneral)).append("\r\n");

            return sb.toString();
        } catch (Exception e) {
            return "Error al generar reporte: " + e.getMessage();
        }
    }

    private String ejecutarReporteMoras() throws SQLException {
        try {
            List<Map<String, Object>> moras = estadisticaM.obtenerMorasPendientes();

            StringBuilder sb = new StringBuilder();
            sb.append("=== REPORTE DE MORAS PENDIENTES ===\r\n\r\n");

            String format = "%-5s %-20s %-12s %-15s %-15s%n";
            sb.append(String.format(format, "ID Cuota", "Cliente", "Días Retraso", "Monto Mora", "Saldo Pend."));
            sb.append("---------------------------------------------------------------------\r\n");

            double totalMoras = 0;
            for (Map<String, Object> mora : moras) {
                sb.append(String.format(format,
                        mora.get("id_cuota"),
                        mora.get("cliente"),
                        mora.get("dias_retraso"),
                        String.format("%.2f", mora.get("monto_mora")),
                        String.format("%.2f", mora.get("saldo_pendiente"))));
                totalMoras += (Double) mora.get("monto_mora");
            }

            sb.append("\r\nTotal Moras Pendientes: ").append(String.format("%.2f", totalMoras)).append("\r\n");

            return sb.toString();
        } catch (Exception e) {
            return "Error al generar reporte: " + e.getMessage();
        }
    }

    private String[] extraerParametros(String parametros) {
        return parametros.replaceAll("\"", "").split(",\\s*");
    }

    private String obtenerComandosDisponibles() {
        return "=== SISTEMA DE VENTAS AL CRÉDITO - RAO MOTOS ===\r\n" +
                "=== Envíe estos comandos en el ASUNTO del correo ===\r\n\r\n" +
                "--- USUARIOS (CU1) ---\r\n" +
                "  CREATEUSUARIO[nombre,email,password,rol,telefono,direccion]\r\n" +
                "    Roles disponibles: PROPIETARIO, PROVEEDOR, CLIENTE\r\n" +
                "    Ej: CREATEUSUARIO[Juan Perez,juan@mail.com,12345678,CLIENTE,77123456,Av. Siempre Viva 123]\r\n" +
                "  GETUSUARIO[id]\r\n" +
                "  LISTARUSUARIOS[*]\r\n" +
                "  UPDATEUSUARIO[id,nombre,email,password,rol,telefono,direccion,true/false]\r\n" +
                "  DELETEUSUARIO[id]\r\n\r\n" +
                "--- PRODUCTOS (CU2) ---\r\n" +
                "  CREATEPRODUCTO[codigo,nombre,marca,modelo,descripcion,precioVentaBase]\r\n" +
                "    Ej: CREATEPRODUCTO[MOT001,Moto Deportiva X,Yamaha,2024,Moto 150cc,15000.00]\r\n" +
                "  GETPRODUCTO[id]\r\n" +
                "  LISTARPRODUCTOS[*]\r\n" +
                "  UPDATEPRODUCTO[id,codigo,nombre,marca,modelo,descripcion,precioVentaBase,true/false]\r\n" +
                "  DELETEPRODUCTO[id]\r\n\r\n" +
                "--- COMPRAS (CU3) ---\r\n" +
                "  CREARCOMPRA[proveedorId,total]\r\n" +
                "    Ej: CREARCOMPRA[1,5000.00]\r\n" +
                "  ANULARCOMPRA[id]\r\n" +
                "  GETCOMPRA[id]\r\n" +
                "  LISTARCOMPRAS[*]\r\n\r\n" +
                "--- PEDIDOS (CU4) ---\r\n" +
                "  CREARPEDIDO[clienteId]\r\n" +
                "    Ej: CREARPEDIDO[1]\r\n" +
                "  DESPACHARPEDIDO[id]\r\n" +
                "  ANULARPEDIDO[id]\r\n" +
                "  GETPEDIDO[id]\r\n" +
                "  LISTARPEDIDOS[*]\r\n\r\n" +
                "--- INVENTARIO (CU5) ---\r\n" +
                "  VERINVENTARIO[*]  (stock de todos los productos)\r\n" +
                "  VERINVENTARIO[productoId]  (stock de un producto)\r\n" +
                "  REGISTRARINGRESO[productoId,cantidad,motivo]\r\n" +
                "    Ej: REGISTRARINGRESO[1,10,Compra a proveedor]\r\n" +
                "  REGISTRAREGRESO[productoId,cantidad,motivo]\r\n" +
                "    Ej: REGISTRAREGRESO[1,2,Venta al cliente]\r\n\r\n" +
                "--- VENTAS (CU6) ---\r\n" +
                "  CREARVENTA_CONTADO[clienteId,fecha,montoTotal,metodoPago]\r\n" +
                "    Metodos de pago: EFECTIVO, QR, TARJETA\r\n" +
                "    Ej: CREARVENTA_CONTADO[1,2026-06-05T10:00:00,15000.00,EFECTIVO]\r\n" +
                "  CREARVENTA_CREDITO[clienteId,fecha,montoTotal,numeroCuotas,tasaInteres,metodoPago]\r\n" +
                "    Ej: CREARVENTA_CREDITO[1,2026-06-05T10:00:00,15000.00,6,5.00,EFECTIVO]\r\n" +
                "  GETVENTA[id]\r\n" +
                "  LISTARVENTAS[*]\r\n" +
                "  DELETEVENTA[id]\r\n\r\n" +
                "--- PAGOS Y CREDITOS (CU7) ---\r\n" +
                "  LISTARCREDITOS[*]\r\n" +
                "  VERCUOTAS[creditoId]\r\n" +
                "  PAGARCUOTA[creditoId,numeroCuota,montoCuota]\r\n" +
                "    Ej: PAGARCUOTA[1,1,2500.00]\r\n\r\n" +
                "--- REPORTES (CU8) ---\r\n" +
                "  REPORT_VENTAS_POR_MES[YYYY-MM]\r\n" +
                "    Ej: REPORT_VENTAS_POR_MES[2026-06]\r\n" +
                "  REPORT_VENTAS_POR_CLIENTE[idCliente]\r\n" +
                "  REPORT_MORAS_PENDIENTES[*]\r\n\r\n" +
                "Envíe HELP para ver esta lista nuevamente.\r\n";
    }
}
