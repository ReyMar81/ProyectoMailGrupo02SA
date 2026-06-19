package org.mailgrupo02.negocio.compras;

import org.mailgrupo02.datos.conexion.Conexion;
import org.mailgrupo02.datos.modelo.*;
import org.mailgrupo02.negocio.inventario.InventarioService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraService {

    public String obtenerCompras() throws SQLException {
        return mapear();
    }

    public String crearCompra(int proveedorId) throws SQLException {
        CompraM compra = new CompraM();
        compra.setProveedorId(proveedorId);
        compra.setTotal(0.0);
        compra.setFecha(new Timestamp(System.currentTimeMillis()));
        compra.setEstado("PENDIENTE");
        int newId = compra.crear();
        return "Compra creada exitosamente (ID: " + newId + ")";
    }

    public String agregarDetalle(int compraId, int productoId, int cantidad, double precioUnitario) throws SQLException {
        CompraM compra = new CompraM().leer(compraId);
        if (compra == null) return "Error: Compra no encontrada.";
        if (!"PENDIENTE".equals(compra.getEstado()))
            return "Error: Solo se pueden agregar detalles a compras en estado PENDIENTE.";

        DetalleCompraM detalle = new DetalleCompraM();
        detalle.setCompraId(compraId);
        detalle.setProductoId(productoId);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.crear();

        double nuevoTotal = compra.getTotal() + (cantidad * precioUnitario);
        compra.setTotal(nuevoTotal);
        compra.actualizar();

        return "Detalle agregado exitosamente a la compra ID " + compraId;
    }

    public CompraN leerCompra(int id) throws SQLException {
        CompraM compra = new CompraM().leer(id);
        if (compra == null) throw new SQLException("Compra no encontrada");

        ProveedorM prov = ProveedorM.leer(compra.getProveedorId());

        CompraN n = new CompraN();
        n.setId(compra.getId());
        n.setProveedorId(compra.getProveedorId());
        n.setProveedorNombre(prov != null ? prov.getRazonSocial() : "N/A");
        n.setFecha(compra.getFecha() != null ? compra.getFecha().toString() : "N/A");
        n.setTotal(compra.getTotal());
        n.setEstado(compra.getEstado());

        DetalleCompraM detM = new DetalleCompraM();
        n.setDetalles(detM.obtenerPorCompra(id));

        return n;
    }

    public String recibirCompra(int id) throws SQLException {
        CompraM compra = new CompraM().leer(id);
        if (compra == null) return "Error: Compra no encontrada.";
        if (!"PENDIENTE".equals(compra.getEstado()))
            return "Error: Solo se puede recibir una compra en estado PENDIENTE.";

        compra.setEstado("RECIBIDA");
        compra.actualizar();

        DetalleCompraM detM = new DetalleCompraM();
        List<DetalleCompraM> detalles = detM.obtenerPorCompra(id);
        InventarioService invService = new InventarioService();
        int cont = 0;
        for (DetalleCompraM d : detalles) {
            invService.registrarIngreso(d.getProductoId(), d.getCantidad(), "Compra #" + id);
            cont++;
        }

        return "Compra recibida exitosamente. " + cont + " producto(s) ingresados al inventario.";
    }

    public String anularCompra(int id) throws SQLException {
        CompraM compra = new CompraM().leer(id);
        if (compra == null) return "Error: Compra no encontrada.";
        if ("RECIBIDA".equals(compra.getEstado()))
            return "Error: No se puede anular una compra ya recibida.";
        compra.setEstado("ANULADA");
        compra.actualizar();
        return "Compra anulada exitosamente";
    }

    private String mapear() throws SQLException {
        String sql = "SELECT c.id, c.proveedor_id, c.fecha, c.total, c.estado, p.razon_social " +
                     "FROM compra c JOIN proveedor p ON c.proveedor_id = p.id ORDER BY c.id";
        List<Object[]> rows = new ArrayList<>();
        String[] headers = {"ID", "Proveedor", "Fecha", "Total", "Estado"};
        int[] widths = {2, 10, 20, 10, 8};

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String prov = rs.getString("razon_social");
                String fecha = rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toString() : "N/A";
                String total = String.format("%.2f", rs.getDouble("total"));
                String estado = rs.getString("estado");
                rows.add(new Object[]{id, prov != null ? prov : "N/A", fecha, total, estado != null ? estado : "N/A"});

                widths[0] = Math.max(widths[0], String.valueOf(id).length());
                widths[1] = Math.max(widths[1], prov != null ? prov.length() : 3);
                widths[2] = Math.max(widths[2], fecha.length());
                widths[3] = Math.max(widths[3], total.length());
                widths[4] = Math.max(widths[4], estado != null ? estado.length() : 3);
            }
        }

        String fmt = "%-" + widths[0] + "s %-" + widths[1] + "s %-" + widths[2] + "s %-" + widths[3] + "s %-" + widths[4] + "s%n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(fmt, (Object[]) headers));
        int totalW = 0;
        for (int w : widths) totalW += w + 1;
        for (int i = 0; i < totalW; i++) sb.append('-');
        sb.append("\r\n");
        for (Object[] row : rows) {
            sb.append(String.format(fmt, row));
        }
        return sb.toString();
    }
}
