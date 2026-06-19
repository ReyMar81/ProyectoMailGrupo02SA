package org.mailgrupo02.presentacion.email;

import java.util.regex.*;

public class PInventario {

    public static String generarHtml(String comando, String resultado) {
        StringBuilder body = new StringBuilder();
        body.append("<h2 class=\"card-title\">").append(describir(comando)).append("</h2>");

        boolean esError = resultado.trim().toLowerCase().startsWith("error");

        if (resultado.contains("---") || resultado.contains("===")) {
            body.append("<div class=\"lista-hdr\">&#128200; Stock Actual</div>")
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
            body.append("<div class=\"ok-card\">")
                .append("<span class=\"ok-icon\">&#10003;</span>")
                .append("<span class=\"ok-tit\">MOVIMIENTO REGISTRADO</span>")
                .append("<span class=\"ok-msg\">")
                .append(resultado.replace("\r\n", "<br>").replace("\n", "<br>"))
                .append("</span></div>");
        }

        return PlantillaBase.envolver("Control de Inventario", body.toString());
    }

    private static String describir(String cmd) {
        if (cmd == null) return "Inventario";
        switch (cmd.toUpperCase()) {
            case "VERINVENTARIO":     return "Estado del Inventario";
            case "REGISTRARINGRESO":  return "Registrar Ingreso de Stock";
            case "REGISTRAREGRESO":   return "Registrar Egreso de Stock";
            default:                  return "Gestión de Inventario";
        }
    }

    private static String escapar(String txt) {
        return txt.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
