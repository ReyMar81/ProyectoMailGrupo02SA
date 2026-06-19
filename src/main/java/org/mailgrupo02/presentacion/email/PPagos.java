package org.mailgrupo02.presentacion.email;

import java.util.regex.*;

public class PPagos {

    public static String generarHtml(String comando, String resultado) {
        StringBuilder body = new StringBuilder();
        body.append("<h2 class=\"card-title\">").append(describir(comando)).append("</h2>");

        boolean esError = resultado.trim().toLowerCase().startsWith("error");

        if (resultado.contains("<img") || resultado.contains("<div style")) {
            body.append("<div class=\"qr-card\">")
                .append("<span class=\"qr-icon\">&#128179;</span>")
                .append("<span class=\"qr-tit\">ESCANEA EL C&Oacute;DIGO QR PARA PAGAR</span>")
                .append("<span class=\"qr-msg\">Apunta la c&aacute;mara al c&oacute;digo para completar el pago</span>")
                .append("</div>")
                .append(resultado);
        } else if (resultado.contains("---") || resultado.contains("===")) {
            body.append("<div class=\"lista-hdr\">&#128184; Resultados</div>")
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

        return PlantillaBase.envolver("Pagos y Cr&eacute;ditos", body.toString());
    }

    private static String describir(String cmd) {
        if (cmd == null) return "Pagos";
        switch (cmd.toUpperCase()) {
            case "LISTARCREDITOS":  return "Créditos Activos";
            case "VERCUOTAS":       return "Cuotas del Crédito";
            case "PAGARCUOTA":      return "Pago de Cuota";
            case "REGISTRARPAGO":   return "Registrar Pago";
            default:                return "Gestión de Pagos y Créditos";
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
