package org.mailgrupo02.sistema.modelo;

import org.mailgrupo02.sistema.conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteM {
    private int id;
    private String nitCi;
    private String tipoCliente;

    public ClienteM() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNitCi() {
        return nitCi;
    }

    public void setNitCi(String nitCi) {
        this.nitCi = nitCi;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String crear() throws SQLException {
        String sql = "INSERT INTO cliente (id, nit_ci, tipo_cliente) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.id);
            pstmt.setString(2, this.nitCi);
            pstmt.setString(3, this.tipoCliente);

            pstmt.executeUpdate();
            return "Cliente creado exitosamente";
        }
    }

    public ClienteM leer(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("id");
                this.nitCi = rs.getString("nit_ci");
                this.tipoCliente = rs.getString("tipo_cliente");
                return this;
            }
            return null;
        }
    }

    public List<ClienteM> obtenerTodos() throws SQLException {
        List<ClienteM> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY id";

        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ClienteM obj = new ClienteM();
                obj.setId(rs.getInt("id"));
                obj.setNitCi(rs.getString("nit_ci"));
                obj.setTipoCliente(rs.getString("tipo_cliente"));
                lista.add(obj);
            }
        }
        return lista;
    }

    public String actualizar() throws SQLException {
        String sql = "UPDATE cliente SET nit_ci = ?, tipo_cliente = ? WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, this.nitCi);
            pstmt.setString(2, this.tipoCliente);
            pstmt.setInt(3, this.id);

            pstmt.executeUpdate();
            return "Cliente actualizado exitosamente";
        }
    }

    public String eliminar(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return "Cliente eliminado exitosamente";
        }
    }
}
