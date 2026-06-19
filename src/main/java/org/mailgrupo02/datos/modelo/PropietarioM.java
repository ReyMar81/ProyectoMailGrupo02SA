package org.mailgrupo02.datos.modelo;

import org.mailgrupo02.datos.conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropietarioM {
    private int id;
    private String nivelAcceso;

    public PropietarioM() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) { this.nivelAcceso = nivelAcceso; }

    public String crear() throws SQLException {
        String sql = "INSERT INTO propietario (id, nivel_acceso) VALUES (?, ?)";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.setString(2, this.nivelAcceso);
            pstmt.executeUpdate();
            return "Propietario creado exitosamente";
        }
    }

    public PropietarioM leer(int id) throws SQLException {
        String sql = "SELECT * FROM propietario WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.id = rs.getInt("id");
                this.nivelAcceso = rs.getString("nivel_acceso");
                return this;
            }
            return null;
        }
    }

    public List<PropietarioM> obtenerTodos() throws SQLException {
        List<PropietarioM> lista = new ArrayList<>();
        String sql = "SELECT * FROM propietario ORDER BY id";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                PropietarioM obj = new PropietarioM();
                obj.setId(rs.getInt("id"));
                obj.setNivelAcceso(rs.getString("nivel_acceso"));
                lista.add(obj);
            }
        }
        return lista;
    }

    public String actualizar() throws SQLException {
        String sql = "UPDATE propietario SET nivel_acceso = ? WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nivelAcceso);
            pstmt.setInt(2, this.id);
            pstmt.executeUpdate();
            return "Propietario actualizado exitosamente";
        }
    }

    public String eliminar(int id) throws SQLException {
        String sql = "DELETE FROM propietario WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return "Propietario eliminado exitosamente";
        }
    }
}
