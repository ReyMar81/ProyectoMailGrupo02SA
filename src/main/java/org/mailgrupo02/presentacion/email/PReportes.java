package org.mailgrupo02.presentacion.email;

public class PReportes {

    public static String generarHtml(String comando, String resultado) {
        StringBuilder body = new StringBuilder();
        body.append("<h2 class=\"card-title\">").append(describir(comando)).append("</h2>");

        boolean esError = resultado.trim().toLowerCase().startsWith("error");

        if (resultado.contains("---") || resultado.contains("===")) {
            body.append("<div class=\"lista-hdr\">&#128202; Datos del Reporte</div>")
                .append("<div class=\"lista-wrap\"><pre class=\"table-pre\">")
                .append(escapar(resultado))
                .append("</pre></div>");
        } else if (esError) {
            body.append("<div class=\"err-card\">")
                .append("<span class=\"err-icon\">&#10007;</span>")
                .append("<span class=\"err-tit\">ERROR AL GENERAR REPORTE</span>")
                .append("<span class=\"err-msg\">")
                .append(resultado.replace("\r\n", "<br>").replace("\n", "<br>"))
                .append("</span></div>");
        } else {
            body.append("<div class=\"ok-card\">")
                .append("<span class=\"ok-icon\">&#128202;</span>")
                .append("<span class=\"ok-tit\">REPORTE GENERADO</span>")
                .append("<span class=\"ok-msg\">")
                .append(resultado.replace("\r\n", "<br>").replace("\n", "<br>"))
                .append("</span></div>");
        }

        return PlantillaBase.envolver("Reportes Gerenciales", body.toString());
    }

    private static String describir(String cmd) {
        if (cmd == null) return "Reportes";
        switch (cmd.toUpperCase()) {
            case "REPORT_VENTAS_POR_MES":     return "Reporte de Ventas por Mes";
            case "REPORT_VENTAS_POR_CLIENTE": return "Reporte de Ventas por Cliente";
            case "REPORT_MORAS_PENDIENTES":   return "Reporte de Moras Pendientes";
            default:                          return "Reportes Gerenciales";
        }
    }

    private static String escapar(String txt) {
        return txt.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
