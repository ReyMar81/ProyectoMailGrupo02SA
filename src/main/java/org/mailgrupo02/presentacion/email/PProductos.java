package org.mailgrupo02.presentacion.email;

import java.util.regex.*;

public class PProductos {

    public static String generarHtml(String comando, String resultado) {
        StringBuilder body = new StringBuilder();
        body.append("<h2 class=\"card-title\">").append(describir(comando)).append("</h2>");

        boolean esError = resultado.trim().toLowerCase().startsWith("error");

        if (resultado.startsWith("<div class=\"detalle")) {
            body.append(resultado);
        } else if (resultado.contains("---") || resultado.contains("===")) {
            body.append("<div class=\"lista-hdr\">&#128230; Resultados</div>")
                .append("<div class=\"lista-wrap\"><pre class=\"table-pre\">")
                .append(escapar(resultado))
                .append("</pre></div>");
        } else if (esError) {
            body.append("<div class=\"err-card\">")
                .append("<span class=\"err-icon\">&#10007;</span>")
                .append("<span class=\"err-tit\">ERROR EN LA OPERACI&Oacute;N</span>")
                .append("<span class=\"err-msg\">")
                .append(resultado.replace("\r\n", "<br>").replace("\n", "<br>"))
                .append("</span></div>");
        } else {
            String idStr = extraerIdTexto(resultado);
            String limpio = idStr != null
                ? resultado.replaceAll("\\s*\\(ID:\\s*\\d+\\)", "").trim()
                : resultado;
            body.append("<div class=\"ok-card\">")
                .append("<span class=\"ok-icon\">&#10003;</span>")
                .append("<span class=\"ok-tit\">OPERACI&Oacute;N EXITOSA</span>")
                .append("<span class=\"ok-msg\">")
                .append(limpio.replace("\r\n", "<br>").replace("\n", "<br>"))
                .append("</span>");
            if (idStr != null)
                body.append("<span class=\"id-badge\">ID: ").append(idStr).append("</span>");
            body.append("</div>");
        }

        return PlantillaBase.envolver("Cat&aacute;logo de Productos", body.toString());
    }

    private static String describir(String cmd) {
        if (cmd == null) return "Productos";
        switch (cmd.toUpperCase()) {
            case "LISTARPRODUCTOS": case "LISTARPRODUCTO": return "Catálogo de Productos";
            case "CREATEPRODUCTO":  return "Registrar Producto";
            case "UPDATEPRODUCTO":  return "Actualizar Producto";
            case "DELETEPRODUCTO":  return "Eliminar Producto";
            case "GETPRODUCTO":     return "Detalle de Producto";
            default:                return "Gestión de Productos";
        }
    }

    private static String escapar(String txt) {
        return txt.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String extraerIdTexto(String msg) {
        Matcher m = Pattern.compile("\\(ID:\\s*(\\d+)\\)").matcher(msg);
        return m.find() ? m.group(1) : null;
    }
}
