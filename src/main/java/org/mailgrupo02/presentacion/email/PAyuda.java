package org.mailgrupo02.presentacion.email;

public class PAyuda {

    public static String generarHtml() {
        StringBuilder c = new StringBuilder();

        c.append("<h2 class=\"card-title\">RAO MOTOS &mdash; Sistema de Ventas por Correo</h2>");
        c.append("<p style=\"color:#4a5568;font-size:17px;margin-bottom:6px;\">")
         .append("Escribe el comando en el <strong>Asunto</strong> del correo a ")
         .append("<strong>grupo02sa@tecnoweb.org.bo</strong>. El cuerpo puede ir vac&iacute;o.</p>");
        c.append("<div class=\"tip\">")
         .append("<strong>&#9432; C&oacute;mo usar:</strong> Escribe el comando con sus par&aacute;metros entre ")
         .append("<code>[ ]</code> separados por comas. <strong>id</strong> siempre es un n&uacute;mero entero. ")
         .append("Los valores de tipo enumerado deben ir en MAY&Uacute;SCULAS.")
         .append("</div>");

        // ── PRIMEROS PASOS ───────────────────────────────────────────────────
        c.append(seccion("&#127381; Primeros pasos &mdash; &iexcl;Empieza aqu&iacute;!", new String[][]{
            {"HELP",
             "Muestra esta p&aacute;gina de ayuda.",
             "HELP"},
            {"CREATEUSUARIO[nombre,email,password,telefono,direccion]",
             "Reg&iacute;strate como cliente. Usa el <strong>mismo email</strong> desde el que env&iacute;as el correo.<br>"
             + "<em>telefono</em> &rarr; texto &nbsp;|&nbsp; <em>direccion</em> &rarr; texto",
             "CREATEUSUARIO[Juan Perez,juan@gmail.com,clave123,70000000,Av. Principal 123]"},
            {"WHOAMI",
             "Muestra tu perfil: ID, nombre, email, rol y datos de contacto.",
             "WHOAMI"},
            {"LISTARPRODUCTOS[*]",
             "Cat&aacute;logo completo de productos disponibles con precios.",
             "LISTARPRODUCTOS[*]"},
            {"GETPRODUCTO[id]",
             "Detalle de un producto espec&iacute;fico.",
             "GETPRODUCTO[1]"},
        }));

        // ── CLIENTE ──────────────────────────────────────────────────────────
        c.append(seccion("&#128101; Comandos para clientes (rol: CLIENTE)", new String[][]{
            {"PEDIDO[productoId:cantidad,...]",
             "Crea un pedido con uno o m&aacute;s productos.<br>"
             + "Formato: <code>productoId:cantidad</code> por cada &iacute;tem, separados con coma.<br>"
             + "El pedido queda en estado <strong>SOLICITADO</strong> hasta que el propietario lo procese.",
             "PEDIDO[1:2,3:1]"},
            {"MISPEDIDOS",
             "Lista todos tus pedidos (historial completo).",
             "MISPEDIDOS"},
            {"MIPEDIDO[pedidoId]",
             "Muestra el detalle de un pedido tuyo con sus productos.<br>"
             + "<em>pedidoId</em> &rarr; n&uacute;mero del pedido.",
             "MIPEDIDO[5]"},
            {"CANCELARPEDIDO[pedidoId]",
             "Cancela un pedido propio en estado <strong>SOLICITADO</strong>.<br>"
             + "No se puede cancelar un pedido ya procesado o despachado.",
             "CANCELARPEDIDO[5]"},
            {"MISCREDITOS",
             "Lista todos tus cr&eacute;ditos activos con saldo pendiente.",
             "MISCREDITOS"},
            {"MISCUOTAS[creditoId]",
             "Muestra las cuotas de uno de tus cr&eacute;ditos (pagadas y pendientes).<br>"
             + "<em>creditoId</em> &rarr; n&uacute;mero del cr&eacute;dito.",
             "MISCUOTAS[2]"},
            {"PAGARCUOTA[creditoId,numeroCuota,montoCuota]",
             "Genera el c&oacute;digo QR de PagoF&aacute;cil para pagar una cuota.<br>"
             + "<em>numeroCuota</em> &rarr; entero &nbsp;|&nbsp; <em>montoCuota</em> &rarr; decimal Bs.",
             "PAGARCUOTA[2,1,708.33]"},
        }));

        // ── PROVEEDOR ────────────────────────────────────────────────────────
        c.append(seccion("&#128666; Comandos para proveedores (rol: PROVEEDOR)", new String[][]{
            {"LISTARCOMPRAS[*]",
             "Lista todas las &oacute;rdenes de compra del sistema.",
             "LISTARCOMPRAS[*]"},
            {"GETCOMPRA[id]",
             "<em>id</em> &rarr; n&uacute;mero de la compra.",
             "GETCOMPRA[1]"},
        }));

        // ── PROPIETARIO ─ Usuarios ───────────────────────────────────────────
        c.append(seccion("&#128273; Gesti&oacute;n de Usuarios (solo PROPIETARIO)", new String[][]{
            {"LISTARUSUARIOS[*]",
             "Lista todos los usuarios del sistema.",
             "LISTARUSUARIOS[*]"},
            {"GETUSUARIO[id]",
             "<em>id</em> &rarr; n&uacute;mero del usuario.",
             "GETUSUARIO[1]"},
            {"CREATEUSUARIO[nombre,email,password,telefono,direccion]",
             "Crea un usuario (siempre como CLIENTE). Usar <code>CAMBIARROL</code> para cambiar su rol despu&eacute;s.",
             "CREATEUSUARIO[Juan Rao,juan@mail.com,clave123,70123456,Av. Banzer 100]"},
            {"UPDATEUSUARIO[id,nombre,email,password,rol,telefono,direccion,activo]",
             "<em>activo</em> &rarr; <code>true</code> / <code>false</code>",
             "UPDATEUSUARIO[1,Juan Rao,juan@mail.com,clave123,CLIENTE,70123456,Av. Banzer 100,true]"},
            {"UPDATECLIENTE[id,nitCi,tipoCliente]",
             "<em>tipoCliente</em> &rarr; <code>REGULAR</code> / <code>FRECUENTE</code> / <code>MAYORISTA</code>",
             "UPDATECLIENTE[3,1234567,FRECUENTE]"},
            {"DELETEUSUARIO[id]",
             "<em>id</em> &rarr; n&uacute;mero del usuario.",
             "DELETEUSUARIO[1]"},
            {"CAMBIARROL[userId,nuevoRol]",
             "Cambia el rol de un usuario y migra sus datos de subtabla.<br>"
             + "<em>nuevoRol</em> &rarr; <code>PROPIETARIO</code> / <code>CLIENTE</code> / <code>PROVEEDOR</code>",
             "CAMBIARROL[5,PROVEEDOR]"},
        }));

        // ── PROPIETARIO ─ Productos ──────────────────────────────────────────
        c.append(seccion("&#128230; Gesti&oacute;n de Productos (solo PROPIETARIO)", new String[][]{
            {"CREATEPRODUCTO[codigo,nombre,marca,modelo,descripcion,precioVentaBase]",
             "<em>codigo</em> &rarr; texto (ej. MOT-001)<br>"
             + "<em>precioVentaBase</em> &rarr; decimal en Bs. (ej. 8500.00)",
             "CREATEPRODUCTO[MOT-001,Moto Sport 150,Honda,CB150,Moto deportiva,8500.00]"},
            {"UPDATEPRODUCTO[id,codigo,nombre,marca,modelo,descripcion,precio,activo]",
             "<em>activo</em> &rarr; <code>true</code> / <code>false</code>",
             "UPDATEPRODUCTO[1,MOT-001,Moto Sport 150,Honda,CB150,Descripcion,8500.00,true]"},
            {"DELETEPRODUCTO[id]",
             "<em>id</em> &rarr; n&uacute;mero.",
             "DELETEPRODUCTO[1]"},
        }));

        // ── PROPIETARIO ─ Pedidos (Admin) ────────────────────────────────────
        c.append(seccion("&#128221; Gesti&oacute;n de Pedidos (solo PROPIETARIO)", new String[][]{
            {"LISTARPEDIDOS[*]",
             "Lista todos los pedidos del sistema.",
             "LISTARPEDIDOS[*]"},
            {"GETPEDIDO[id]",
             "Ver detalle de un pedido por ID.",
             "GETPEDIDO[1]"},
            {"PROCESARPEDIDO[id,CONTADO,metodoPago]",
             "Convierte un pedido en venta al contado.<br>"
             + "<em>metodoPago</em> &rarr; <code>EFECTIVO</code> / <code>QR</code> / <code>TARJETA</code>",
             "PROCESARPEDIDO[3,CONTADO,EFECTIVO]"},
            {"PROCESARPEDIDO[id,CREDITO,cuotas,tasaInteres,metodoPago]",
             "Convierte un pedido en venta a cr&eacute;dito.<br>"
             + "<em>cuotas</em> &rarr; entero (ej. 12) &nbsp;|&nbsp; <em>tasaInteres</em> &rarr; % (ej. 5.0)",
             "PROCESARPEDIDO[3,CREDITO,12,5.0,QR]"},
            {"DESPACHARPEDIDO[id]",
             "Marca el pedido como despachado (entregado).",
             "DESPACHARPEDIDO[1]"},
            {"ANULARPEDIDO[id]",
             "Anula un pedido.",
             "ANULARPEDIDO[1]"},
        }));

        // ── PROPIETARIO ─ Compras ────────────────────────────────────────────
        c.append(seccion("&#128666; Gesti&oacute;n de Compras (solo PROPIETARIO)", new String[][]{
            {"CREARCOMPRA[proveedorId,total]",
             "<em>proveedorId</em> &rarr; n&uacute;mero &nbsp;|&nbsp; <em>total</em> &rarr; decimal Bs.",
             "CREARCOMPRA[2,15000.00]"},
            {"ANULARCOMPRA[id]",
             "Anula una compra.",
             "ANULARCOMPRA[1]"},
        }));

        // ── PROPIETARIO ─ Inventario ─────────────────────────────────────────
        c.append(seccion("&#128200; Inventario (solo PROPIETARIO)", new String[][]{
            {"VERINVENTARIO[*]",
             "Stock actual de todos los productos.",
             "VERINVENTARIO[*]"},
            {"VERINVENTARIO[productoId]",
             "Stock de un producto espec&iacute;fico.",
             "VERINVENTARIO[1]"},
            {"REGISTRARINGRESO[productoId,cantidad,motivo]",
             "<em>cantidad</em> &rarr; entero &nbsp;|&nbsp; <em>motivo</em> &rarr; texto libre",
             "REGISTRARINGRESO[1,10,Compra nueva]"},
            {"REGISTRAREGRESO[productoId,cantidad,motivo]",
             "<em>cantidad</em> &rarr; entero &nbsp;|&nbsp; <em>motivo</em> &rarr; texto libre",
             "REGISTRAREGRESO[1,2,Venta directa]"},
        }));

        // ── PROPIETARIO ─ Ventas ─────────────────────────────────────────────
        c.append(seccion("&#128176; Ventas directas (solo PROPIETARIO)", new String[][]{
            {"LISTARVENTAS[*]",
             "Lista todas las ventas.",
             "LISTARVENTAS[*]"},
            {"GETVENTA[id]",
             "Detalle de venta incluyendo cuotas si es cr&eacute;dito.",
             "GETVENTA[1]"},
            {"CREARVENTA_CONTADO[clienteId,fecha,montoTotal,metodoPago]",
             "<em>fecha</em> &rarr; <code>YYYY-MM-DDThh:mm:ss</code><br>"
             + "<em>metodoPago</em> &rarr; <code>EFECTIVO</code> / <code>QR</code> / <code>TARJETA</code>",
             "CREARVENTA_CONTADO[3,2026-06-18T10:00:00,5000.00,EFECTIVO]"},
            {"CREARVENTA_CREDITO[clienteId,fecha,montoTotal,nroCuotas,tasaInteres,metodoPago]",
             "<em>nroCuotas</em> &rarr; entero &nbsp;|&nbsp; <em>tasaInteres</em> &rarr; % (ej. 5.0)",
             "CREARVENTA_CREDITO[3,2026-06-18T10:00:00,8500.00,12,5.0,QR]"},
            {"DELETEVENTA[id]",
             "Elimina una venta.",
             "DELETEVENTA[1]"},
        }));

        // ── PROPIETARIO ─ Cr&eacute;ditos ────────────────────────────────────
        c.append(seccion("&#128184; Cr&eacute;ditos (solo PROPIETARIO)", new String[][]{
            {"LISTARCREDITOS[*]",
             "Lista todos los cr&eacute;ditos con saldo pendiente.",
             "LISTARCREDITOS[*]"},
            {"VERCUOTAS[creditoId]",
             "Cuotas de un cr&eacute;dito por ID.",
             "VERCUOTAS[1]"},
        }));

        // ── PROPIETARIO ─ Reportes ───────────────────────────────────────────
        c.append(seccion("&#128202; Reportes (solo PROPIETARIO)", new String[][]{
            {"REPORT_VENTAS_POR_MES[YYYY-MM]",
             "Ventas del mes con totales por tipo (contado/cr&eacute;dito).",
             "REPORT_VENTAS_POR_MES[2026-06]"},
            {"REPORT_VENTAS_POR_CLIENTE[clienteId]",
             "Historial de ventas de un cliente.",
             "REPORT_VENTAS_POR_CLIENTE[3]"},
            {"REPORT_MORAS_PENDIENTES[*]",
             "Cuotas vencidas con d&iacute;as de retraso y mora.",
             "REPORT_MORAS_PENDIENTES[*]"},
        }));

        return PlantillaBase.envolver("Sistema de Ventas por Correo &bull; Grupo 02 SA", c.toString());
    }

    public static String generarError(String mensaje) {
        String contenido =
            "<h2 class=\"card-title\">Acceso denegado o comando no reconocido</h2>" +
            "<div class=\"alert alert-error\">" +
            "<strong>NO SE PUDO PROCESAR EL COMANDO</strong><br>" +
            mensaje.replace("\n", "<br>") +
            "</div>" +
            "<p style=\"margin-top:14px;font-size:17px;color:#4a5568;\">Env&iacute;e <code>HELP</code> en el asunto para ver la lista completa de comandos disponibles para tu rol.</p>";
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
