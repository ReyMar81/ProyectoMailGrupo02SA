package org.mailgrupo02.presentacion.email.controladores;

import org.mailgrupo02.datos.modelo.CreditoM;
import org.mailgrupo02.datos.modelo.VentaM;
import org.mailgrupo02.negocio.ventas.VentaService;
import org.mailgrupo02.presentacion.email.PVentas;

import java.sql.Timestamp;
import java.util.List;

public class VentaControlador {

    public static boolean canHandle(String cmd) {
        if (cmd == null) return false;
        switch (cmd.toUpperCase()) {
            case "LISTARVENTAS": case "LISTARVENTA":
            case "CREARVENTA_CONTADO":
            case "CREARVENTA_CREDITO":
            case "GETVENTA":
            case "DELETEVENTA":
                return true;
            default:
                return false;
        }
    }

    public static String handle(String cmd, List<String> params) {
        try {
            VentaService service = new VentaService(new VentaM(), new CreditoM());
            String rawResult;

            switch (cmd.toUpperCase()) {
                case "LISTARVENTAS":
                case "LISTARVENTA":
                    rawResult = service.obtenerVentas();
                    break;

                case "GETVENTA": {
                    if (params.isEmpty()) return PVentas.generarHtml(cmd, "Error: se requiere el ID de la venta.");
                    rawResult = service.leerVenta(Integer.parseInt(params.get(0).trim())).toString();
                    break;
                }

                case "CREARVENTA_CONTADO":
                    if (params.size() < 4) return PVentas.generarHtml(cmd, "Error: se requieren 4 parámetros [clienteId,fecha,montoTotal,metodoPago].");
                    rawResult = service.crearVentaContado(
                        Integer.parseInt(params.get(0).trim()),
                        Timestamp.valueOf(params.get(1).trim()),
                        Double.parseDouble(params.get(2).trim()),
                        params.get(3).trim());
                    break;

                case "CREARVENTA_CREDITO":
                    if (params.size() < 6) return PVentas.generarHtml(cmd, "Error: se requieren 6 parámetros [clienteId,fecha,montoTotal,nroCuotas,tasaInteres,metodoPago].");
                    rawResult = service.crearVentaCredito(
                        Integer.parseInt(params.get(0).trim()),
                        Timestamp.valueOf(params.get(1).trim()),
                        Double.parseDouble(params.get(2).trim()),
                        Integer.parseInt(params.get(3).trim()),
                        Double.parseDouble(params.get(4).trim()),
                        params.get(5).trim());
                    break;

                case "DELETEVENTA":
                    if (params.isEmpty()) return PVentas.generarHtml(cmd, "Error: se requiere el ID de la venta.");
                    rawResult = service.eliminarVenta(Integer.parseInt(params.get(0).trim()));
                    break;

                default:
                    rawResult = "Error: Comando de ventas no soportado.";
            }

            return PVentas.generarHtml(cmd, rawResult);
        } catch (Exception e) {
            return PVentas.generarHtml(cmd, "Error: " + e.getMessage());
        }
    }
}
