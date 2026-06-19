package org.mailgrupo02.presentacion.email;

import org.mailgrupo02.presentacion.email.controladores.UsuarioControlador;
import org.mailgrupo02.presentacion.email.controladores.ProductoControlador;
import org.mailgrupo02.presentacion.email.controladores.VentaControlador;
import org.mailgrupo02.presentacion.email.controladores.CompraControlador;
import org.mailgrupo02.presentacion.email.controladores.PedidoControlador;
import org.mailgrupo02.presentacion.email.controladores.InventarioControlador;
import org.mailgrupo02.presentacion.email.controladores.PagoControlador;
import org.mailgrupo02.presentacion.email.controladores.ReporteControlador;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComandoEmailNuevo {

    public ComandoEmailNuevo() throws SQLException {
        // Inicialización delegada a los controladores
    }

    /**
     * Punto de entrada principal: parsea el asunto del correo, detecta el controlador
     * responsable y devuelve la respuesta HTML formateada.
     */
    public String evaluarYEjecutar(String asunto) {
        try {
            if (asunto == null || asunto.trim().isEmpty()) {
                return PAyuda.generarError("El asunto del correo está vacío.");
            }

            asunto = asunto.trim();

            if (asunto.equalsIgnoreCase("HELP")) {
                return PAyuda.generarHtml();
            }

            String[] parsed = parsearAsunto(asunto);
            String cmd = parsed[0].toUpperCase();
            List<String> params = parsed[1].isEmpty()
                    ? Collections.emptyList()
                    : Arrays.asList(parsed[1].replaceAll("\"", "").split(",\\s*"));

            if (UsuarioControlador.canHandle(cmd))   return UsuarioControlador.handle(cmd, params);
            if (ProductoControlador.canHandle(cmd))  return ProductoControlador.handle(cmd, params);
            if (VentaControlador.canHandle(cmd))     return VentaControlador.handle(cmd, params);
            if (CompraControlador.canHandle(cmd))    return CompraControlador.handle(cmd, params);
            if (PedidoControlador.canHandle(cmd))    return PedidoControlador.handle(cmd, params);
            if (InventarioControlador.canHandle(cmd))return InventarioControlador.handle(cmd, params);
            if (PagoControlador.canHandle(cmd))      return PagoControlador.handle(cmd, params);
            if (ReporteControlador.canHandle(cmd))   return ReporteControlador.handle(cmd, params);

            return PAyuda.generarError(
                "Comando no reconocido: <strong>" + asunto + "</strong><br>" +
                "Envíe <strong>HELP</strong> en el asunto para ver todos los comandos disponibles.");

        } catch (Exception e) {
            return PAyuda.generarError("Error inesperado al procesar el comando: " + e.getMessage());
        }
    }

    /**
     * Separa el nombre del comando de sus parámetros.
     * Ejemplo: "CREATEUSUARIO[Juan,mail,pass,ROL,tel,dir]"
     *        → ["CREATEUSUARIO", "Juan,mail,pass,ROL,tel,dir"]
     */
    private static String[] parsearAsunto(String asunto) {
        int inicio = asunto.indexOf('[');
        if (inicio == -1) {
            return new String[]{asunto, ""};
        }
        String cmd = asunto.substring(0, inicio).trim();
        int fin = asunto.lastIndexOf(']');
        String params = (fin > inicio) ? asunto.substring(inicio + 1, fin) : "";
        return new String[]{cmd, params};
    }
}
