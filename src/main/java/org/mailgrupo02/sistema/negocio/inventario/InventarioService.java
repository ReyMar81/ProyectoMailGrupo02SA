package org.mailgrupo02.sistema.negocio.inventario;

import org.mailgrupo02.sistema.conexion.Conexion;
import org.mailgrupo02.sistema.modelo.InventarioM;
import org.mailgrupo02.sistema.modelo.ProductoM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class InventarioService {

    public String verInventario() throws SQLException {
        List<InventarioM> lista = new InventarioM().obtenerTodos();
        return mapear(lista);
    }

    public String verInventarioPorProducto(int productoId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.conectar();
            String sql = "SELECT i.*, p.nombre FROM inventario i JOIN producto p ON i.producto_id = p.id WHERE i.producto_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productoId);
            rs = pstmt.executeQuery();
            StringBuilder sb = new StringBuilder();
            String format = "%-5s %-30s %-12s %-20s %-15s%n";
            sb.append(String.format(format, "ID", "Producto", "Stock", "Tecnica Inventario", "Tecnica Costo"));
            sb.append("------------------------------------------------------------------------------\r\n");
            while (rs.next()) {
                sb.append(String.format(format,
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("stock_actual"),
                        rs.getString("tecnica_inventario") != null ? rs.getString("tecnica_inventario") : "N/A",
                        rs.getString("tecnica_costo") != null ? rs.getString("tecnica_costo") : "N/A"));
            }
            return sb.toString();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                }
        }
    }

    public String registrarIngreso(int productoId, int cantidad, String motivo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.conectar();
            String checkSql = "SELECT id, stock_actual FROM inventario WHERE producto_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, productoId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int inventarioId = rs.getInt("id");
                int stockActual = rs.getInt("stock_actual");
                rs.close();

                String updateSql = "UPDATE inventario SET stock_actual = ?, fecha_actualizacion = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, stockActual + cantidad);
                pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(3, inventarioId);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement(
                        "INSERT INTO movimiento_inventario (inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (?, ?, ?, ?, ?)");
                pstmt.setInt(1, inventarioId);
                pstmt.setString(2, "INGRESO");
                pstmt.setInt(3, cantidad);
                pstmt.setString(4, motivo);
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();

                return "Ingreso registrado exitosamente";
            } else {
                rs.close();

                pstmt = conn.prepareStatement(
                        "INSERT INTO inventario (producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (?, ?, ?, ?, ?)");
                pstmt.setInt(1, productoId);
                pstmt.setInt(2, cantidad);
                pstmt.setString(3, "PERMANENTE");
                pstmt.setString(4, "PROMEDIO");
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();

                String idSql = "SELECT currval(pg_get_serial_sequence('inventario','id'))";
                pstmt = conn.prepareStatement(idSql);
                rs = pstmt.executeQuery();
                int inventarioId = 0;
                if (rs.next())
                    inventarioId = rs.getInt(1);
                rs.close();

                pstmt = conn.prepareStatement(
                        "INSERT INTO movimiento_inventario (inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (?, ?, ?, ?, ?)");
                pstmt.setInt(1, inventarioId);
                pstmt.setString(2, "INGRESO");
                pstmt.setInt(3, cantidad);
                pstmt.setString(4, motivo);
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();

                return "Inventario creado e ingreso registrado exitosamente";
            }
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                }
        }
    }

    public String registrarEgreso(int productoId, int cantidad, String motivo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.conectar();
            String checkSql = "SELECT id, stock_actual FROM inventario WHERE producto_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, productoId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int inventarioId = rs.getInt("id");
                int stockActual = rs.getInt("stock_actual");
                rs.close();

                if (stockActual < cantidad) {
                    return "Stock insuficiente: disponible " + stockActual + ", solicitado " + cantidad;
                }

                String updateSql = "UPDATE inventario SET stock_actual = ?, fecha_actualizacion = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, stockActual - cantidad);
                pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(3, inventarioId);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement(
                        "INSERT INTO movimiento_inventario (inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (?, ?, ?, ?, ?)");
                pstmt.setInt(1, inventarioId);
                pstmt.setString(2, "EGRESO");
                pstmt.setInt(3, cantidad);
                pstmt.setString(4, motivo);
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();

                return "Egreso registrado exitosamente";
            } else {
                return "No existe inventario para el producto especificado";
            }
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                }
        }
    }

    private String mapear(List<InventarioM> lista) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-30s %-12s %-20s %-15s%n";
        sb.append(String.format(format, "ID", "Producto", "Stock", "Tecnica Inventario", "Tecnica Costo"));
        sb.append("------------------------------------------------------------------------------\r\n");
        for (InventarioM inv : lista) {
            String nombreProducto;
            try {
                ProductoM prod = ProductoM.leer(inv.getProductoId());
                nombreProducto = prod != null ? prod.getNombre() : "N/A";
            } catch (SQLException e) {
                nombreProducto = "N/A";
            }
            sb.append(String.format(format,
                    inv.getId(),
                    nombreProducto,
                    inv.getStockActual(),
                    inv.getTecnicaInventario() != null ? inv.getTecnicaInventario() : "N/A",
                    inv.getTecnicaCosto() != null ? inv.getTecnicaCosto() : "N/A"));
        }
        return sb.toString();
    }
}
