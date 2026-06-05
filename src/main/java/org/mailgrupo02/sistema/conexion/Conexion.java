package org.mailgrupo02.sistema.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Conexion instancia;
    private Connection connection;
    private String url = "jdbc:postgresql://mail.tecnoweb.org.bo:5432/db_grupo02sa";
    private String usuario = "grupo02sa";
    private String contraseña = "grup002grup002*";

    private Conexion() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (ClassNotFoundException ex) {
            throw new SQLException(ex);
        }
    }

    public static Conexion getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new Conexion();
        } else if (instancia.getConnection().isClosed()) {
            instancia = new Conexion();
        }

        return instancia;
    }

    // Alias para compatibilidad con código existente
    public static Conexion getInstance() throws SQLException {
        return getInstancia();
    }

    // Método estático para obtener conexión directamente
    public static Connection conectar() throws SQLException {
        return getInstancia().getConnection();
    }

    public Connection getConnection() {
        return connection;
    }
}