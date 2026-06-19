package org.mailgrupo02.presentacion.email.controladores;

import org.mailgrupo02.negocio.pedidos.PedidoService;
import org.mailgrupo02.presentacion.email.PPedidos;

import java.util.List;

public class PedidoControlador {

    public static boolean canHandle(String cmd) {
        if (cmd == null) return false;
        switch (cmd.toUpperCase()) {
            case "LISTARPEDIDOS": case "LISTARPEDIDO":
            case "CREARPEDIDO":
            case "DESPACHARPEDIDO":
            case "ANULARPEDIDO":
            case "GETPEDIDO":
                return true;
            default:
                return false;
        }
    }

    public static String handle(String cmd, List<String> params) {
        try {
            PedidoService service = new PedidoService();
            String rawResult;

            switch (cmd.toUpperCase()) {
                case "LISTARPEDIDOS":
                case "LISTARPEDIDO":
                    rawResult = service.obtenerPedidos();
                    break;

                case "GETPEDIDO": {
                    if (params.isEmpty()) return PPedidos.generarHtml(cmd, "Error: se requiere el ID del pedido.");
                    rawResult = service.leerPedido(Integer.parseInt(params.get(0).trim())).toString();
                    break;
                }

                case "CREARPEDIDO":
                    if (params.isEmpty()) return PPedidos.generarHtml(cmd, "Error: se requiere el ID del cliente.");
                    rawResult = service.crearPedido(Integer.parseInt(params.get(0).trim()));
                    break;

                case "DESPACHARPEDIDO":
                    if (params.isEmpty()) return PPedidos.generarHtml(cmd, "Error: se requiere el ID del pedido.");
                    rawResult = service.despacharPedido(Integer.parseInt(params.get(0).trim()));
                    break;

                case "ANULARPEDIDO":
                    if (params.isEmpty()) return PPedidos.generarHtml(cmd, "Error: se requiere el ID del pedido.");
                    rawResult = service.anularPedido(Integer.parseInt(params.get(0).trim()));
                    break;

                default:
                    rawResult = "Error: Comando de pedidos no soportado.";
            }

            return PPedidos.generarHtml(cmd, rawResult);
        } catch (Exception e) {
            return PPedidos.generarHtml(cmd, "Error: " + e.getMessage());
        }
    }
}
