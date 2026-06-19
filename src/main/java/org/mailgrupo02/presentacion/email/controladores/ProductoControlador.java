package org.mailgrupo02.presentacion.email.controladores;

import org.mailgrupo02.datos.modelo.ProductoM;
import org.mailgrupo02.negocio.productos.ProductoService;
import org.mailgrupo02.negocio.productos.ProductoN;
import org.mailgrupo02.presentacion.email.PProductos;

import java.util.List;

public class ProductoControlador {

    public static boolean canHandle(String cmd) {
        if (cmd == null) return false;
        switch (cmd.toUpperCase()) {
            case "LISTARPRODUCTOS": case "LISTARPRODUCTO":
            case "CREATEPRODUCTO":
            case "UPDATEPRODUCTO":
            case "DELETEPRODUCTO":
            case "GETPRODUCTO":
                return true;
            default:
                return false;
        }
    }

    public static String handle(String cmd, List<String> params) {
        try {
            ProductoService service = new ProductoService(new ProductoM());
            String rawResult;

            switch (cmd.toUpperCase()) {
                case "LISTARPRODUCTOS":
                case "LISTARPRODUCTO":
                    rawResult = service.obtenerProductos();
                    break;

                case "GETPRODUCTO": {
                    if (params.isEmpty()) return PProductos.generarHtml(cmd, "Error: se requiere el ID del producto.");
                    int id = Integer.parseInt(params.get(0).trim());
                    ProductoN p = service.leerProducto(id);
                    rawResult = "=== DATOS DEL PRODUCTO ===\r\n\r\n" +
                                "ID:              " + p.getId()              + "\r\n" +
                                "Código:          " + nvl(p.getCodigo())     + "\r\n" +
                                "Nombre:          " + nvl(p.getNombre())     + "\r\n" +
                                "Marca:           " + nvl(p.getMarca())      + "\r\n" +
                                "Modelo:          " + nvl(p.getModelo())     + "\r\n" +
                                "Descripción:     " + nvl(p.getDescripcion()) + "\r\n" +
                                "Precio Vta Base: " + String.format("%.2f Bs.", p.getPrecioVentaBase()) + "\r\n" +
                                "Activo:          " + (p.isActivo() ? "Sí" : "No") + "\r\n" +
                                "Fecha Reg.:      " + nvl(p.getFechaReg());
                    break;
                }

                case "CREATEPRODUCTO":
                    if (params.size() < 6) return PProductos.generarHtml(cmd, "Error: se requieren 6 parámetros [codigo,nombre,marca,modelo,descripcion,precioVentaBase].");
                    rawResult = service.agregarProducto(
                        params.get(0), params.get(1), params.get(2),
                        params.get(3), params.get(4), Double.parseDouble(params.get(5).trim()));
                    break;

                case "UPDATEPRODUCTO":
                    if (params.size() < 8) return PProductos.generarHtml(cmd, "Error: se requieren 8 parámetros [id,codigo,nombre,marca,modelo,descripcion,precio,activo].");
                    rawResult = service.actualizarProducto(
                        Integer.parseInt(params.get(0).trim()), params.get(1), params.get(2),
                        params.get(3), params.get(4), params.get(5),
                        Double.parseDouble(params.get(6).trim()),
                        Boolean.parseBoolean(params.get(7).trim()));
                    break;

                case "DELETEPRODUCTO":
                    if (params.isEmpty()) return PProductos.generarHtml(cmd, "Error: se requiere el ID del producto.");
                    rawResult = service.eliminarProducto(Integer.parseInt(params.get(0).trim()));
                    break;

                default:
                    rawResult = "Error: Comando de productos no soportado.";
            }

            return PProductos.generarHtml(cmd, rawResult);
        } catch (Exception e) {
            return PProductos.generarHtml(cmd, "Error: " + e.getMessage());
        }
    }

    private static String nvl(String val) {
        return val != null ? val : "N/A";
    }
}
