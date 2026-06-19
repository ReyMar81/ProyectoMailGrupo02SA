package org.mailgrupo02.presentacion.email.controladores;

import org.mailgrupo02.negocio.pagos.PagoCuotaService;
import org.mailgrupo02.presentacion.email.PPagos;

import java.util.List;

public class PagoControlador {

    public static boolean canHandle(String cmd) {
        if (cmd == null) return false;
        switch (cmd.toUpperCase()) {
            case "LISTARCREDITOS":
            case "VERCUOTAS":
            case "PAGARCUOTA":
            case "REGISTRARPAGO":
                return true;
            default:
                return false;
        }
    }

    public static String handle(String cmd, List<String> params) {
        try {
            PagoCuotaService service = new PagoCuotaService();
            String rawResult;

            switch (cmd.toUpperCase()) {
                case "LISTARCREDITOS":
                    rawResult = service.listarCreditos();
                    break;

                case "VERCUOTAS":
                    if (params.isEmpty()) return PPagos.generarHtml(cmd, "Error: se requiere el ID del crédito.");
                    rawResult = service.verCuotas(Integer.parseInt(params.get(0).trim()));
                    break;

                case "PAGARCUOTA":
                case "REGISTRARPAGO":
                    if (params.size() < 3) return PPagos.generarHtml(cmd, "Error: se requieren 3 parámetros [creditoId,numeroCuota,montoCuota].");
                    rawResult = service.registrarPago(
                        Integer.parseInt(params.get(0).trim()),
                        Integer.parseInt(params.get(1).trim()),
                        Double.parseDouble(params.get(2).trim()));
                    break;

                default:
                    rawResult = "Error: Comando de pagos no soportado.";
            }

            return PPagos.generarHtml(cmd, rawResult);
        } catch (Exception e) {
            return PPagos.generarHtml(cmd, "Error: " + e.getMessage());
        }
    }
}
