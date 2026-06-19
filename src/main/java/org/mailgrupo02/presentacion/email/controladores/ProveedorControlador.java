package org.mailgrupo02.presentacion.email.controladores;

import org.mailgrupo02.datos.conexion.Conexion;
import org.mailgrupo02.datos.modelo.ProveedorM;
import org.mailgrupo02.datos.modelo.UsuarioM;
import org.mailgrupo02.presentacion.email.PlantillaBase;

import java.sql.*;
import java.util.List;

public class ProveedorControlador {

    public static boolean canHandle(String cmd) {
        if (cmd == null) return false;
        switch (cmd.toUpperCase()) {
            case "CREATEPROVEEDOR":
            case "LISTARPROVEEDORES":
            case "GETPROVEEDOR":
            case "DELETEPROVEEDOR":
                return true;
            default: return false;
        }
    }

    public static String handle(String cmd, List<String> params) {
        try {
            switch (cmd.toUpperCase()) {

                case "LISTARPROVEEDORES":
                    return html("Listado de Proveedores", listar());

                case "GETPROVEEDOR": {
                    if (params.isEmpty()) return html("Error", "Error: se requiere el ID del proveedor.");
                    return html("Detalle de Proveedor", detalle(Integer.parseInt(params.get(0).trim())));
                }

                case "CREATEPROVEEDOR": {
                    if (params.isEmpty()) return html("Error",
                        "Error: se requieren al menos 2 parámetros [razonSocial,contacto,telefono].");
                    String razonSocial = params.get(0).trim();
                    String contacto    = params.size() > 1 ? params.get(1).trim() : "";
                    String telefono    = params.size() > 2 ? params.get(2).trim() : "";
                    return html("Registrar Proveedor", crear(razonSocial, contacto, telefono));
                }

                case "DELETEPROVEEDOR": {
                    if (params.isEmpty()) return html("Error", "Error: se requiere el ID del proveedor.");
                    return html("Eliminar Proveedor", eliminar(Integer.parseInt(params.get(0).trim())));
                }

                default:
                    return html("Error", "Comando de proveedor no soportado.");
            }
        } catch (Exception e) {
            return html("Error", "Error: " + e.getMessage());
        }
    }

    // ── Crear: usuario interno (sin login) + subtabla proveedor ──────────────
    private static String crear(String razonSocial, String contacto, String telefono) throws SQLException {
        UsuarioM u = new UsuarioM();
        u.setNombre(razonSocial);
        u.setEmail("prv-" + System.currentTimeMillis() + "@rao.interno");
        u.setPassword("*");
        u.setRol("PROVEEDOR");
        u.setTelefono(telefono);
        u.setActivo(true);
        int userId = UsuarioM.crear(u);

        ProveedorM p = new ProveedorM();
        p.setId(userId);
        p.setRazonSocial(razonSocial);
        p.setContactoPrincipal(contacto);
        p.crear();

        return "Proveedor registrado exitosamente (ID: " + userId + ")";
    }

    // ── Listar: JOIN usuario + proveedor, muestra campos de negocio ───────────
    private static String listar() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String fmt = "%-5s %-30s %-25s %-15s %-6s%n";
        sb.append(String.format(fmt, "ID", "Razón Social", "Contacto", "Teléfono", "Activo"));
        sb.append("--------------------------------------------------------------------------------------------\r\n");
        String sql = "SELECT u.id, p.razon_social, p.contacto_principal, u.telefono, u.activo " +
                     "FROM proveedor p JOIN usuario u ON p.id = u.id ORDER BY u.id";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append(String.format(fmt,
                    rs.getInt("id"),
                    nvl(rs.getString("razon_social")),
                    nvl(rs.getString("contacto_principal")),
                    nvl(rs.getString("telefono")),
                    rs.getBoolean("activo") ? "SI" : "NO"));
            }
        }
        return sb.toString();
    }

    // ── Detalle de un proveedor ───────────────────────────────────────────────
    private static String detalle(int id) throws SQLException {
        String sql = "SELECT u.id, p.razon_social, p.contacto_principal, u.telefono, u.activo " +
                     "FROM proveedor p JOIN usuario u ON p.id = u.id WHERE u.id = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return "Razón Social: " + nvl(rs.getString("razon_social")) +
                           " | Contacto: " + nvl(rs.getString("contacto_principal")) +
                           " | Teléfono: " + nvl(rs.getString("telefono")) +
                           " | Activo: " + (rs.getBoolean("activo") ? "SI" : "NO") +
                           " (ID: " + id + ")";
                }
                return "Error: Proveedor no encontrado.";
            }
        }
    }

    // ── Eliminar: borra el usuario (CASCADE elimina la subtabla) ─────────────
    private static String eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ? AND rol = 'PROVEEDOR'";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0
                ? "Proveedor eliminado exitosamente (ID: " + id + ")"
                : "Error: Proveedor no encontrado o ID no corresponde a un proveedor.";
        }
    }

    // ── HTML wrapper ──────────────────────────────────────────────────────────
    private static String html(String titulo, String resultado) {
        StringBuilder body = new StringBuilder();
        body.append(PlantillaBase.titulo(titulo));
        boolean esError = resultado.trim().toLowerCase().startsWith("error");
        if (resultado.contains("---") || resultado.contains("===")) {
            body.append(PlantillaBase.tablaHtml("&#128666;", resultado));
        } else if (esError) {
            body.append(PlantillaBase.errCard(resultado));
        } else {
            String idStr = PlantillaBase.extraerId(resultado);
            String limpio = idStr != null ? resultado.replaceAll("\\s*\\(ID:\\s*\\d+\\)", "").trim() : resultado;
            body.append(PlantillaBase.okCard(limpio, idStr));
        }
        return PlantillaBase.envolver("Gesti&oacute;n de Proveedores", body.toString());
    }

    private static String nvl(String s) { return s != null ? s : ""; }
}
