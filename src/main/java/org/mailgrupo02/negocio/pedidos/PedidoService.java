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

    public String crearPedido(int clienteId) throws SQLException {
        PedidoM pedido = new PedidoM();
        pedido.setClienteId(clienteId);
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));
        pedido.setEstado("SOLICITADO");
        return PedidoM.crear(pedido);
    }

    public String agregarDetalle(int pedidoId, int productoId, int cantidad) throws SQLException {
        PedidoDetalleM detalle = new PedidoDetalleM();
        detalle.setPedidoId(pedidoId);
        detalle.setProductoId(productoId);
        detalle.setCantidad(cantidad);
        return PedidoDetalleM.crear(detalle);
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

    private String mapear(List<PedidoM> lista) {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-22s %-12s%n";
        sb.append(String.format(format, "ID", "Cliente", "Fecha", "Estado"));
        sb.append("---------------------------------------------------------\r\n");
        for (PedidoM p : lista) {
            sb.append(String.format(format,
                    p.getId(),
                    p.getClienteId(),
                    p.getFecha() != null ? p.getFecha().toString() : "N/A",
                    p.getEstado() != null ? p.getEstado() : "N/A"));
        }
        return sb.toString();
    }
}
