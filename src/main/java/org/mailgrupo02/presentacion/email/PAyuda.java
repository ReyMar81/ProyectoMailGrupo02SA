package org.mailgrupo02.presentacion.email;

public class PAyuda {

    public static String generarHtml() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("<h2 class=\"card-title\">Comandos Disponibles - RAO MOTOS</h2>");
        contenido.append("<p style=\"color:#6b7280;font-size:13px;margin-bottom:20px;\">")
                 .append("Envíe el comando en el <strong>Asunto</strong> del correo a <em>grupo02sa@tecnoweb.org.bo</em></p>");

        contenido.append(seccion("Usuarios (CU1)",
            new String[][]{
                {"LISTARUSUARIOS[*]","Listar todos los usuarios"},
                {"GETUSUARIO[id]","Ver detalle de un usuario"},
                {"CREATEUSUARIO[nombre,email,password,rol,telefono,direccion]","Crear usuario (rol: PROPIETARIO/PROVEEDOR/CLIENTE)"},
                {"UPDATEUSUARIO[id,nombre,email,password,rol,telefono,direccion,activo]","Actualizar usuario"},
                {"DELETEUSUARIO[id]","Eliminar usuario"}
            }));

        contenido.append(seccion("Productos (CU2)",
            new String[][]{
                {"LISTARPRODUCTOS[*]","Listar todos los productos"},
                {"GETPRODUCTO[id]","Ver detalle de un producto"},
                {"CREATEPRODUCTO[codigo,nombre,marca,modelo,descripcion,precioVentaBase]","Crear producto"},
                {"UPDATEPRODUCTO[id,codigo,nombre,marca,modelo,descripcion,precio,activo]","Actualizar producto"},
                {"DELETEPRODUCTO[id]","Eliminar producto"}
            }));

        contenido.append(seccion("Compras (CU3)",
            new String[][]{
                {"LISTARCOMPRAS[*]","Listar todas las compras"},
                {"GETCOMPRA[id]","Ver detalle de una compra"},
                {"CREARCOMPRA[proveedorId,total]","Crear compra (Ej: CREARCOMPRA[1,5000.00])"},
                {"ANULARCOMPRA[id]","Anular una compra"}
            }));

        contenido.append(seccion("Pedidos (CU4)",
            new String[][]{
                {"LISTARPEDIDOS[*]","Listar todos los pedidos"},
                {"GETPEDIDO[id]","Ver detalle de un pedido"},
                {"CREARPEDIDO[clienteId]","Crear pedido (Ej: CREARPEDIDO[1])"},
                {"DESPACHARPEDIDO[id]","Marcar pedido como despachado"},
                {"ANULARPEDIDO[id]","Anular un pedido"}
            }));

        contenido.append(seccion("Inventario (CU5)",
            new String[][]{
                {"VERINVENTARIO[*]","Ver stock de todos los productos"},
                {"VERINVENTARIO[productoId]","Ver stock de un producto específico"},
                {"REGISTRARINGRESO[productoId,cantidad,motivo]","Registrar entrada de stock"},
                {"REGISTRAREGRESO[productoId,cantidad,motivo]","Registrar salida de stock"}
            }));

        contenido.append(seccion("Ventas (CU6)",
            new String[][]{
                {"LISTARVENTAS[*]","Listar todas las ventas"},
                {"GETVENTA[id]","Ver detalle de una venta"},
                {"CREARVENTA_CONTADO[clienteId,fecha,montoTotal,metodoPago]","Venta al contado (fecha: 2026-06-05T10:00:00, método: EFECTIVO/QR/TARJETA)"},
                {"CREARVENTA_CREDITO[clienteId,fecha,montoTotal,nroCuotas,tasaInteres,metodoPago]","Venta a crédito"},
                {"DELETEVENTA[id]","Eliminar venta"}
            }));

        contenido.append(seccion("Pagos y Créditos (CU7)",
            new String[][]{
                {"LISTARCREDITOS[*]","Listar todos los créditos activos"},
                {"VERCUOTAS[creditoId]","Ver cuotas de un crédito"},
                {"PAGARCUOTA[creditoId,numeroCuota,montoCuota]","Pagar cuota (genera QR de PagoFácil)"}
            }));

        contenido.append(seccion("Reportes (CU8)",
            new String[][]{
                {"REPORT_VENTAS_POR_MES[YYYY-MM]","Reporte de ventas de un mes (Ej: REPORT_VENTAS_POR_MES[2026-06])"},
                {"REPORT_VENTAS_POR_CLIENTE[clienteId]","Historial de ventas de un cliente"},
                {"REPORT_MORAS_PENDIENTES[*]","Reporte de moras y cuotas vencidas"}
            }));

        return construirPlantillaBase(contenido.toString());
    }

    public static String generarError(String mensaje) {
        String contenido = "<h2 class=\"card-title\">Comando no reconocido</h2>" +
                "<div class=\"alert alert-error\">" +
                "<strong>NO SE PUDO PROCESAR EL COMANDO</strong><br>" +
                mensaje.replace("\n", "<br>") +
                "</div>" +
                "<p style=\"margin-top:15px;font-size:13px;color:#6b7280;\">Envíe <code>HELP</code> en el asunto para ver todos los comandos disponibles.</p>";
        return construirPlantillaBase(contenido);
    }

    private static String seccion(String titulo, String[][] comandos) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"section\">")
          .append("<h3 class=\"section-title\">").append(titulo).append("</h3>")
          .append("<table>");
        sb.append("<tr><th>Comando</th><th>Descripción</th></tr>");
        for (String[] cmd : comandos) {
            sb.append("<tr>")
              .append("<td><code class=\"cmd\">").append(cmd[0]).append("</code></td>")
              .append("<td>").append(cmd[1]).append("</td>")
              .append("</tr>");
        }
        sb.append("</table></div>");
        return sb.toString();
    }

    private static String construirPlantillaBase(String contenido) {
        return "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"utf-8\">\n<style>\n" +
               "body{font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;background:#f1f5f9;color:#1e293b;margin:0;padding:0;}\n" +
               ".container{max-width:680px;margin:30px auto;background:#fff;border-radius:16px;overflow:hidden;box-shadow:0 8px 30px rgba(0,0,0,0.10);border:1px solid #e2e8f0;}\n" +
               ".header{background:linear-gradient(135deg,#b91c1c,#7f1d1d);padding:30px 20px;text-align:center;color:#fff;}\n" +
               ".header h1{margin:0;font-size:22px;font-weight:700;letter-spacing:1px;}\n" +
               ".header p{margin:6px 0 0;font-size:13px;opacity:0.85;}\n" +
               ".content{padding:30px 28px;}\n" +
               ".card-title{font-size:18px;font-weight:600;margin-top:0;margin-bottom:16px;color:#b91c1c;border-bottom:2px solid #fee2e2;padding-bottom:8px;}\n" +
               ".section{margin-bottom:24px;}\n" +
               ".section-title{font-size:14px;font-weight:700;color:#fff;background:#4b5563;padding:8px 14px;border-radius:6px;margin:0 0 10px 0;}\n" +
               ".alert{padding:16px;border-radius:12px;margin-bottom:20px;font-size:14px;line-height:1.6;}\n" +
               ".alert-error{background:#fef2f2;border:1px solid #fecaca;color:#991b1b;}\n" +
               "table{width:100%;border-collapse:collapse;margin-top:0;font-size:13px;}\n" +
               "th{background:#374151;color:#fff;font-weight:600;text-align:left;padding:10px 14px;}\n" +
               "td{padding:9px 14px;border-bottom:1px solid #f1f5f9;color:#374151;}\n" +
               "tr:last-child td{border-bottom:none;}\n" +
               "tr:nth-child(even){background:#f8fafc;}\n" +
               ".cmd{font-family:'Courier New',monospace;font-size:11px;background:#f1f5f9;color:#1d4ed8;padding:2px 6px;border-radius:4px;white-space:nowrap;}\n" +
               "code{font-family:'Courier New',monospace;background:#f1f5f9;padding:2px 5px;border-radius:3px;}\n" +
               ".footer{background:#f8fafc;padding:20px;text-align:center;font-size:12px;color:#64748b;border-top:1px solid #e2e8f0;}\n" +
               "</style>\n</head>\n<body>\n" +
               "<div class=\"container\">\n" +
               "<div class=\"header\"><h1>RAO MOTOS</h1><p>Sistema de Ventas por Correo Electrónico</p></div>\n" +
               "<div class=\"content\">" + contenido + "</div>\n" +
               "<div class=\"footer\"><strong>Grupo 02 &mdash; Tecnología Web (UAGRM)</strong><br>Correo automático &mdash; no responder directamente.</div>\n" +
               "</div>\n</body>\n</html>";
    }
}
