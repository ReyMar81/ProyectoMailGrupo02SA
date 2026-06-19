package org.mailgrupo02.presentacion.email;

public class PAyuda {

    public static String generarHtml() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("<h2 class=\"card-title\">Comandos Disponibles &mdash; RAO MOTOS</h2>");
        contenido.append("<p style=\"color:#4a5568;font-size:17px;margin-bottom:14px;\">")
                 .append("Escribe el comando en el <strong>Asunto</strong> del correo a ")
                 .append("<strong>grupo02sa@tecnoweb.org.bo</strong>. El cuerpo puede ir vac&iacute;o.</p>");
        contenido.append("<div class=\"tip\">")
                 .append("<strong>&#9432; C&oacute;mo usar:</strong> Escribe el comando con sus par&aacute;metros entre <code>[ ]</code> separados por comas. ")
                 .append("<strong>id</strong> siempre es un n&uacute;mero entero del registro en la base de datos. ")
                 .append("Los valores deben respetar exactamente las opciones indicadas (MAY&Uacute;SCULAS).")
                 .append("</div>");

        contenido.append(seccion("&#128101; Usuarios", new String[][]{
            {"LISTARUSUARIOS[*]",
             "Lista todos los usuarios registrados.",
             "LISTARUSUARIOS[*]"},
            {"GETUSUARIO[id]",
             "<em>id</em> &rarr; n&uacute;mero (ej. 1, 2, 3&hellip;)",
             "GETUSUARIO[1]"},
            {"CREATEUSUARIO[nombre,email,password,rol,telefono,direccion]",
             "<em>rol</em> &rarr; <code>PROPIETARIO</code> / <code>PROVEEDOR</code> / <code>CLIENTE</code><br>"
             + "<em>telefono</em> &rarr; texto &nbsp;|&nbsp; <em>direccion</em> &rarr; texto",
             "CREATEUSUARIO[Juan Rao,juan@mail.com,clave123,CLIENTE,70123456,Av. Banzer 100]"},
            {"UPDATEUSUARIO[id,nombre,email,password,rol,telefono,direccion,activo]",
             "<em>id</em> &rarr; n&uacute;mero &nbsp;|&nbsp; <em>activo</em> &rarr; <code>true</code> / <code>false</code>",
             "UPDATEUSUARIO[1,Juan Rao,juan@mail.com,clave123,CLIENTE,70123456,Av. Banzer 100,true]"},
            {"UPDATECLIENTE[id,nitCi,tipoCliente]",
             "<em>id</em> &rarr; n&uacute;mero (ID del cliente)<br>"
             + "<em>nitCi</em> &rarr; NIT o CI (ej. <code>1234567</code>)<br>"
             + "<em>tipoCliente</em> &rarr; <code>REGULAR</code> / <code>FRECUENTE</code> / <code>MAYORISTA</code>",
             "UPDATECLIENTE[3,1234567,FRECUENTE]"},
            {"DELETEUSUARIO[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "DELETEUSUARIO[1]"},
        }));

        contenido.append(seccion("&#128230; Productos", new String[][]{
            {"LISTARPRODUCTOS[*]",
             "Lista todos los productos del cat&aacute;logo.",
             "LISTARPRODUCTOS[*]"},
            {"GETPRODUCTO[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "GETPRODUCTO[1]"},
            {"CREATEPRODUCTO[codigo,nombre,marca,modelo,descripcion,precioVentaBase]",
             "<em>codigo</em> &rarr; texto (ej. MOT-001)<br>"
             + "<em>precioVentaBase</em> &rarr; decimal en Bs. (ej. 8500.00)",
             "CREATEPRODUCTO[MOT-001,Moto Sport 150,Honda,CB150,Moto deportiva,8500.00]"},
            {"UPDATEPRODUCTO[id,codigo,nombre,marca,modelo,descripcion,precio,activo]",
             "<em>id</em> &rarr; n&uacute;mero &nbsp;|&nbsp; <em>precio</em> &rarr; decimal Bs.<br>"
             + "<em>activo</em> &rarr; <code>true</code> / <code>false</code>",
             "UPDATEPRODUCTO[1,MOT-001,Moto Sport 150,Honda,CB150,Moto deportiva,8500.00,true]"},
            {"DELETEPRODUCTO[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "DELETEPRODUCTO[1]"},
        }));

        contenido.append(seccion("&#128666; Compras a Proveedores", new String[][]{
            {"LISTARCOMPRAS[*]",
             "Lista todas las compras registradas.",
             "LISTARCOMPRAS[*]"},
            {"GETCOMPRA[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "GETCOMPRA[1]"},
            {"CREARCOMPRA[proveedorId,total]",
             "<em>proveedorId</em> &rarr; n&uacute;mero (ID del proveedor)<br>"
             + "<em>total</em> &rarr; decimal en Bs.",
             "CREARCOMPRA[2,15000.00]"},
            {"ANULARCOMPRA[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "ANULARCOMPRA[1]"},
        }));

        contenido.append(seccion("&#128221; Pedidos", new String[][]{
            {"LISTARPEDIDOS[*]",
             "Lista todos los pedidos.",
             "LISTARPEDIDOS[*]"},
            {"GETPEDIDO[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "GETPEDIDO[1]"},
            {"CREARPEDIDO[clienteId]",
             "<em>clienteId</em> &rarr; n&uacute;mero (ID del cliente)",
             "CREARPEDIDO[3]"},
            {"DESPACHARPEDIDO[id]",
             "Cambia el estado a despachado.<br><em>id</em> &rarr; n&uacute;mero",
             "DESPACHARPEDIDO[1]"},
            {"ANULARPEDIDO[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "ANULARPEDIDO[1]"},
        }));

        contenido.append(seccion("&#128200; Inventario", new String[][]{
            {"VERINVENTARIO[*]",
             "Muestra el stock actual de todos los productos.",
             "VERINVENTARIO[*]"},
            {"VERINVENTARIO[productoId]",
             "<em>productoId</em> &rarr; n&uacute;mero (ID del producto)",
             "VERINVENTARIO[1]"},
            {"REGISTRARINGRESO[productoId,cantidad,motivo]",
             "<em>productoId</em> &rarr; n&uacute;mero &nbsp;|&nbsp; <em>cantidad</em> &rarr; entero<br>"
             + "<em>motivo</em> &rarr; texto libre",
             "REGISTRARINGRESO[1,10,Compra nueva]"},
            {"REGISTRAREGRESO[productoId,cantidad,motivo]",
             "<em>productoId</em> &rarr; n&uacute;mero &nbsp;|&nbsp; <em>cantidad</em> &rarr; entero<br>"
             + "<em>motivo</em> &rarr; texto libre",
             "REGISTRAREGRESO[1,2,Venta directa]"},
        }));

        contenido.append(seccion("&#128176; Ventas", new String[][]{
            {"LISTARVENTAS[*]",
             "Lista todas las ventas registradas.",
             "LISTARVENTAS[*]"},
            {"GETVENTA[id]",
             "Muestra detalle de la venta y cuotas si es a cr&eacute;dito.<br><em>id</em> &rarr; n&uacute;mero",
             "GETVENTA[1]"},
            {"CREARVENTA_CONTADO[clienteId,fecha,montoTotal,metodoPago]",
             "<em>clienteId</em> &rarr; n&uacute;mero<br>"
             + "<em>fecha</em> &rarr; formato: <code>YYYY-MM-DDThh:mm:ss</code><br>"
             + "<em>montoTotal</em> &rarr; decimal Bs.<br>"
             + "<em>metodoPago</em> &rarr; <code>EFECTIVO</code> / <code>QR</code> / <code>TARJETA</code>",
             "CREARVENTA_CONTADO[3,2026-06-18T10:00:00,5000.00,EFECTIVO]"},
            {"CREARVENTA_CREDITO[clienteId,fecha,montoTotal,nroCuotas,tasaInteres,metodoPago]",
             "<em>clienteId</em> &rarr; n&uacute;mero<br>"
             + "<em>fecha</em> &rarr; <code>YYYY-MM-DDThh:mm:ss</code><br>"
             + "<em>nroCuotas</em> &rarr; entero (ej. 6, 12, 24)<br>"
             + "<em>tasaInteres</em> &rarr; decimal % (ej. <code>5.0</code>)<br>"
             + "<em>metodoPago</em> &rarr; <code>EFECTIVO</code> / <code>QR</code> / <code>TARJETA</code>",
             "CREARVENTA_CREDITO[3,2026-06-18T10:00:00,8500.00,12,5.0,QR]"},
            {"DELETEVENTA[id]",
             "<em>id</em> &rarr; n&uacute;mero",
             "DELETEVENTA[1]"},
        }));

        contenido.append(seccion("&#128184; Pagos y Cr&eacute;ditos", new String[][]{
            {"LISTARCREDITOS[*]",
             "Lista todos los cr&eacute;ditos activos con su saldo pendiente.",
             "LISTARCREDITOS[*]"},
            {"VERCUOTAS[creditoId]",
             "Muestra todas las cuotas (pagadas y pendientes).<br>"
             + "<em>creditoId</em> &rarr; n&uacute;mero (ID del cr&eacute;dito)",
             "VERCUOTAS[1]"},
            {"PAGARCUOTA[creditoId,numeroCuota,montoCuota]",
             "<em>creditoId</em> &rarr; n&uacute;mero &nbsp;|&nbsp; <em>numeroCuota</em> &rarr; entero<br>"
             + "<em>montoCuota</em> &rarr; decimal Bs. (monto exacto)<br>"
             + "Genera c&oacute;digo QR de PagoF&aacute;cil en la respuesta.",
             "PAGARCUOTA[1,1,708.33]"},
        }));

        contenido.append(seccion("&#128202; Reportes", new String[][]{
            {"REPORT_VENTAS_POR_MES[YYYY-MM]",
             "Reporte de ventas del mes con totales por tipo (contado/cr&eacute;dito).<br>"
             + "<em>YYYY-MM</em> &rarr; a&ntilde;o y mes (ej. <code>2026-06</code>)",
             "REPORT_VENTAS_POR_MES[2026-06]"},
            {"REPORT_VENTAS_POR_CLIENTE[clienteId]",
             "Historial completo de ventas de un cliente.<br>"
             + "<em>clienteId</em> &rarr; n&uacute;mero (ID del cliente)",
             "REPORT_VENTAS_POR_CLIENTE[3]"},
            {"REPORT_MORAS_PENDIENTES[*]",
             "Lista las cuotas vencidas con d&iacute;as de retraso y mora acumulada.",
             "REPORT_MORAS_PENDIENTES[*]"},
        }));

        return PlantillaBase.envolver("Sistema de Ventas por Correo &bull; Grupo 02 SA", contenido.toString());
    }

    public static String generarError(String mensaje) {
        String contenido = "<h2 class=\"card-title\">Comando no reconocido</h2>" +
                "<div class=\"alert alert-error\">" +
                "<strong>NO SE PUDO PROCESAR EL COMANDO</strong><br>" +
                mensaje.replace("\n", "<br>") +
                "</div>" +
                "<p style=\"margin-top:14px;font-size:17px;color:#4a5568;\">Env&iacute;e <code>HELP</code> en el asunto para ver la lista completa de comandos.</p>";
        return PlantillaBase.envolver("Sistema de Ventas por Correo &bull; Grupo 02 SA", contenido);
    }

    private static String seccion(String titulo, String[][] comandos) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"section\">")
          .append("<span class=\"section-title\">").append(titulo).append("</span>")
          .append("<table>")
          .append("<tr>")
          .append("<th style=\"width:34%\">Asunto del correo</th>")
          .append("<th style=\"width:36%\">Descripci&oacute;n y par&aacute;metros</th>")
          .append("<th style=\"width:30%\">Ejemplo copiable</th>")
          .append("</tr>");
        for (String[] cmd : comandos) {
            sb.append("<tr>")
              .append("<td><code class=\"cmd\">").append(cmd[0]).append("</code></td>")
              .append("<td style=\"font-size:15px;line-height:1.7;color:#374151;\">").append(cmd[1]).append("</td>")
              .append("<td><code class=\"ejemplo\">").append(cmd[2]).append("</code></td>")
              .append("</tr>");
        }
        sb.append("</table></div>");
        return sb.toString();
    }
}
