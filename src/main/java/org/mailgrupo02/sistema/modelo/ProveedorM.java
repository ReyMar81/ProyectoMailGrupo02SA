package org.mailgrupo02.sistema.modelo;

import org.mailgrupo02.sistema.conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorM {
    private int id;
    private String razonSocial;
    private String contactoPrincipal;

    public ProveedorM() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String crear() throws SQLException {
        String sql = "INSERT INTO proveedor (id, razon_social, contacto_principal) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.id);
            pstmt.setString(2, this.razonSocial);
            pstmt.setString(3, this.contactoPrincipal);

            pstmt.executeUpdate();
            return "Proveedor creado exitosamente";
        }
    }

    public ProveedorM leer(int id) throws SQLException {
        String sql = "SELECT * FROM proveedor WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("id");
                this.razonSocial = rs.getString("razon_social");
                this.contactoPrincipal = rs.getString("contacto_principal");
                return this;
            }
            return null;
        }
    }

    public List<ProveedorM> obtenerTodos() throws SQLException {
        List<ProveedorM> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor ORDER BY id";

        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ProveedorM obj = new ProveedorM();
                obj.setId(rs.getInt("id"));
                obj.setRazonSocial(rs.getString("razon_social"));
                obj.setContactoPrincipal(rs.getString("contacto_principal"));
                lista.add(obj);
            }
        }
        return lista;
    }

    public String actualizar() throws SQLException {
        String sql = "UPDATE proveedor SET razon_social = ?, contacto_principal = ? WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, this.razonSocial);
            pstmt.setString(2, this.contactoPrincipal);
            pstmt.setInt(3, this.id);

            pstmt.executeUpdate();
            return "Proveedor actualizado exitosamente";
        }
    }

    public String eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proveedor WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return "Proveedor eliminado exitosamente";
        }
    }
}
