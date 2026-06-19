package org.mailgrupo02.negocio.pedidos;

import org.mailgrupo02.datos.modelo.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class PedidoService {

    public String obtenerPedidos() throws SQLException {
        List<PedidoM> lista = PedidoM.obtenerTodos();
        return mapear(lista);
    }

    /** Admin: crea pedido vacío para un cliente (por clienteId). */
    public String crearPedido(int clienteId) throws SQLException {
        PedidoM pedido = new PedidoM();
        pedido.setClienteId(clienteId);
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));
        pedido.setEstado("SOLICITADO");
        int id = PedidoM.crear(pedido);
        return "Pedido creado con éxito (ID: " + id + ")";
    }

    /**
     * Cliente: crea un pedido con sus productos en una sola operación.
     * items = array de [productoId, cantidad].
     */
    public String crearConProductos(int clienteId, int[][] items) throws SQLException {
        if (items == null || items.length == 0) {
            return "Error: debe especificar al menos un producto.";
        }
        PedidoM pedido = new PedidoM();
        pedido.setClienteId(clienteId);
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));
        pedido.setEstado("SOLICITADO");
        int pedidoId = PedidoM.crear(pedido);

        StringBuilder resumen = new StringBuilder();
        for (int[] item : items) {
            int productoId = item[0];
            int cantidad   = item[1];
            ProductoM p = ProductoM.leer(productoId);
            if (p == null || !p.isActivo()) {
                return "Error: producto ID " + productoId + " no existe o está inactivo.";
            }
            PedidoDetalleM det = new PedidoDetalleM();
            det.setPedidoId(pedidoId);
            det.setProductoId(productoId);
            det.setCantidad(cantidad);
            PedidoDetalleM.crear(det);
            resumen.append("\n  • ").append(p.getNombre())
                   .append(" x").append(cantidad)
                   .append(" — Bs. ").append(String.format("%.2f", p.getPrecioVentaBase() * cantidad));
        }
        return "Pedido registrado exitosamente (ID: " + pedidoId + ")" + resumen.toString();
    }

    /** Cliente: lista sus propios pedidos. */
    public String obtenerPorCliente(int clienteId) throws SQLException {
        List<PedidoM> lista = PedidoM.obtenerPorCliente(clienteId);
        if (lista.isEmpty()) return "No tienes pedidos registrados.";
        return mapear(lista);
    }

    public PedidoN leerPedido(int id) throws SQLException {
        PedidoM pedido = PedidoM.leer(id);
        PedidoN n = new PedidoN();
        n.setId(pedido.getId());
        n.setClienteId(pedido.getClienteId());
        n.setFecha(pedido.getFecha() != null ? pedido.getFecha().toString() : "N/A");
        n.setEstado(pedido.getEstado());
        n.setDetalles(PedidoDetalleM.obtenerPorPedido(id));
        return n;
    }

    /** Cliente: obtiene su pedido verificando que le pertenezca. */
    public String leerPedidoCliente(int pedidoId, int clienteId) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no pertenece a tu cuenta.";
        PedidoN n = leerPedido(pedidoId);
        return formatearDetalle(n);
    }

    /** Cliente: cancela su pedido solo si está en estado SOLICITADO. */
    public String cancelarPorCliente(int pedidoId, int clienteId) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no pertenece a tu cuenta.";
        if (!"SOLICITADO".equals(p.getEstado())) {
            return "Error: solo puedes cancelar pedidos en estado SOLICITADO. Estado actual: " + p.getEstado();
        }
        p.setEstado("ANULADO");
        return PedidoM.actualizar(p);
    }

    public String despacharPedido(int id) throws SQLException {
        PedidoM pedido = PedidoM.leer(id);
        pedido.setEstado("DESPACHADO");
        return PedidoM.actualizar(pedido);
    }

    public String anularPedido(int id) throws SQLException {
        PedidoM pedido = PedidoM.leer(id);
        pedido.setEstado("ANULADO");
        return PedidoM.actualizar(pedido);
    }

    public String agregarDetalle(int pedidoId, int productoId, int cantidad) throws SQLException {
        PedidoDetalleM det = new PedidoDetalleM();
        det.setPedidoId(pedidoId);
        det.setProductoId(productoId);
        det.setCantidad(cantidad);
        return PedidoDetalleM.crear(det);
    }

    private String mapear(List<PedidoM> lista) {
        StringBuilder sb = new StringBuilder();
        String fmt = "%-5s %-10s %-22s %-12s%n";
        sb.append(String.format(fmt, "ID", "Cliente", "Fecha", "Estado"));
        sb.append("---------------------------------------------------------\r\n");
        for (PedidoM p : lista) {
            sb.append(String.format(fmt,
                p.getId(), p.getClienteId(),
                p.getFecha() != null ? p.getFecha().toString() : "N/A",
                p.getEstado() != null ? p.getEstado() : "N/A"));
        }
        return sb.toString();
    }

    private String formatearDetalle(PedidoN n) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d | Estado: %s | Fecha: %s%n",
            n.getId(), n.getEstado(), n.getFecha()));
        sb.append("---------------------------------------------------------\r\n");
        List<PedidoDetalleM> detalles = n.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            sb.append("Sin productos registrados.\r\n");
        } else {
            String fmt = "%-5s %-30s %-8s%n";
            sb.append(String.format(fmt, "ProdID", "Producto", "Cantidad"));
            sb.append("-----------------------------------------\r\n");
            for (PedidoDetalleM d : detalles) {
                try {
                    ProductoM p = ProductoM.leer(d.getProductoId());
                    String nombre = p != null ? p.getNombre() : "ID:" + d.getProductoId();
                    sb.append(String.format(fmt, d.getProductoId(), nombre, d.getCantidad()));
                } catch (Exception e) {
                    sb.append(String.format(fmt, d.getProductoId(), "N/A", d.getCantidad()));
                }
            }
        }
        return sb.toString();
    }
}
