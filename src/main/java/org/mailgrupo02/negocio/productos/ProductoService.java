package org.mailgrupo02.negocio.productos;

import org.mailgrupo02.datos.conexion.Conexion;
import org.mailgrupo02.datos.modelo.ProductoM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    public ProductoService(ProductoM productoM) {
    }

    public String obtenerProductos() throws SQLException {
        return mapear();
    }

    public ProductoN leerProducto(int id) throws SQLException {
        ProductoM productoMObj = ProductoM.leer(id);
        return mapearUno(productoMObj);
    }

    public String agregarProducto(String codigo, String nombre, String marca, String modelo,
            String descripcion, double precioVentaBase) throws SQLException {
        ProductoValidator.validarCampos(codigo, nombre, marca, modelo, descripcion, precioVentaBase);
        ProductoM productoMObj = cargar(0, codigo, nombre, marca, modelo, descripcion, precioVentaBase, true);
        int id = ProductoM.crear(productoMObj);
        return "Producto creado con éxito (ID: " + id + ")";
    }

    public String actualizarProducto(int id, String codigo, String nombre, String marca, String modelo,
            String descripcion, double precioVentaBase, boolean activo) throws SQLException {
        ProductoValidator.validarCampos(codigo, nombre, marca, modelo, descripcion, precioVentaBase);
        ProductoM productoMObj = cargar(id, codigo, nombre, marca, modelo, descripcion, precioVentaBase, activo);
        return ProductoM.actualizar(productoMObj);
    }

    public String eliminarProducto(int id) throws SQLException {
        return ProductoM.eliminar(id);
    }

    private String mapear() throws SQLException {
        String sql = "SELECT p.*, i.stock_actual FROM producto p " +
                     "LEFT JOIN inventario i ON p.id = i.producto_id ORDER BY p.id";
        List<Object[]> rows = new ArrayList<>();
        String[] headers = {"ID", "C\u00f3digo", "Nombre", "Marca", "Modelo", "Precio", "Stock"};
        int[] widths = {2, 8, 10, 8, 8, 10, 5};

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String cod = rs.getString("codigo");
                String nom = rs.getString("nombre");
                String mar = rs.getString("marca");
                String mod = rs.getString("modelo");
                String pre = String.format("%.2f", rs.getDouble("precio_venta_base"));
                int stock = rs.getInt("stock_actual");
                String stockStr = rs.wasNull() ? "0" : String.valueOf(stock);
                rows.add(new Object[]{id, cod != null ? cod : "N/A", nom != null ? nom : "", mar != null ? mar : "N/A",
                        mod != null ? mod : "N/A", pre, stockStr});

                widths[0] = Math.max(widths[0], String.valueOf(id).length());
                widths[1] = Math.max(widths[1], cod != null ? cod.length() : 3);
                widths[2] = Math.max(widths[2], nom != null ? nom.length() : 4);
                widths[3] = Math.max(widths[3], mar != null ? mar.length() : 3);
                widths[4] = Math.max(widths[4], mod != null ? mod.length() : 3);
                widths[5] = Math.max(widths[5], pre.length());
                widths[6] = Math.max(widths[6], stockStr.length());
            }
        }

        StringBuilder sb = new StringBuilder();
        String sep = "  ";
        StringBuilder fmtSb = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            if (i > 0) fmtSb.append(sep);
            fmtSb.append("%-").append(widths[i]).append("s");
        }
        fmtSb.append("%n");
        String fmt = fmtSb.toString();
        sb.append(String.format(fmt, (Object[]) headers));
        int totalW = 0;
        for (int i = 0; i < widths.length; i++) {
            totalW += widths[i];
            if (i < widths.length - 1) totalW += sep.length();
        }
        for (int i = 0; i < totalW; i++) sb.append('-');
        sb.append("\r\n");
        for (Object[] row : rows) {
            sb.append(String.format(fmt, row));
        }
        return sb.toString();
    }

    private ProductoN mapearUno(ProductoM productoM) throws SQLException {
        ProductoN productoN = new ProductoN();
        productoN.setId(productoM.getId());
        productoN.setCodigo(productoM.getCodigo());
        productoN.setNombre(productoM.getNombre());
        productoN.setMarca(productoM.getMarca());
        productoN.setModelo(productoM.getModelo());
        productoN.setDescripcion(productoM.getDescripcion());
        productoN.setPrecioVentaBase(productoM.getPrecioVentaBase());
        productoN.setFotoUrl(productoM.getFotoUrl());
        productoN.setActivo(productoM.isActivo());
        productoN.setFechaReg(productoM.getFechaReg() != null ? productoM.getFechaReg().toString() : null);
        String sql = "SELECT stock_actual FROM inventario WHERE producto_id = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productoM.getId());
            try (ResultSet rs = ps.executeQuery()) {
                productoN.setStockActual(rs.next() ? rs.getInt("stock_actual") : 0);
            }
        }
        return productoN;
    }

    private ProductoM cargar(int id, String codigo, String nombre, String marca, String modelo,
            String descripcion, double precioVentaBase, boolean activo) throws SQLException {
        ProductoM productoMObj = new ProductoM();
        productoMObj.setId(id);
        productoMObj.setCodigo(codigo);
        productoMObj.setNombre(nombre);
        productoMObj.setMarca(marca);
        productoMObj.setModelo(modelo);
        productoMObj.setDescripcion(descripcion);
        productoMObj.setPrecioVentaBase(precioVentaBase);
        productoMObj.setActivo(activo);
        return productoMObj;
    }
}
