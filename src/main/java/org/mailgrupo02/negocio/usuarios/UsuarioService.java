package org.mailgrupo02.negocio.usuarios;

import org.mailgrupo02.datos.modelo.*;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    public UsuarioService(UsuarioM usuarioM) {
    }

    public String obtenerUsuarios() throws SQLException {
        List<UsuarioM> usuarios = UsuarioM.obtenerTodos();
        return mapear(usuarios);
    }

    public UsuarioN leerUsuario(int id) throws SQLException {
        UsuarioM usuarioMObj = UsuarioM.leer(id);
        return mapearUno(usuarioMObj);
    }

    public String agregarUsuario(String nombre, String email, String password, String rol,
            String telefono, String direccion) throws SQLException {
        UsuarioValidator.validarCampos(nombre, email, password, rol);
        UsuarioM usuarioMObj = cargar(0, nombre, email, password, rol, telefono, direccion, true);
        int userId = UsuarioM.crear(usuarioMObj);

        switch (rol) {
            case "CLIENTE": {
                ClienteM c = new ClienteM();
                c.setId(userId);
                c.setNitCi("N/A");
                c.setTipoCliente("REGULAR");
                c.crear();
                break;
            }
            case "PROVEEDOR": {
                ProveedorM p = new ProveedorM();
                p.setId(userId);
                p.setRazonSocial(nombre);
                p.setContactoPrincipal(nombre);
                p.crear();
                break;
            }
            case "PROPIETARIO": {
                PropietarioM p = new PropietarioM();
                p.setId(userId);
                p.setNivelAcceso("TOTAL");
                p.crear();
                break;
            }
        }

        return "Usuario creado con éxito (ID: " + userId + ")";
    }

    public String actualizarUsuario(int id, String nombre, String email, String password,
            String rol, String telefono, String direccion, boolean activo) throws SQLException {
        UsuarioValidator.validarCampos(nombre, email, password, rol);
        UsuarioM usuarioMObj = cargar(id, nombre, email, password, rol, telefono, direccion, activo);
        return UsuarioM.actualizar(usuarioMObj);
    }

    public String eliminarUsuario(int id) throws SQLException {
        return UsuarioM.eliminar(id);
    }

    private String mapear(List<UsuarioM> usuarios) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-20s %-30s %-15s %-10s %-15s%n";
        sb.append(String.format(format, "ID", "Nombre", "Email", "Rol", "Activo", "Teléfono"));
        sb.append(
                "------------------------------------------------------------------------------------------------------\r\n");

        for (UsuarioM usuario : usuarios) {
            sb.append(String.format(format,
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getRol(),
                    usuario.isActivo() ? "Sí" : "No",
                    usuario.getTelefono() != null ? usuario.getTelefono() : "N/A"));
        }
        return sb.toString();
    }

    private UsuarioN mapearUno(UsuarioM usuarioM) throws SQLException {
        UsuarioN usuarioN = new UsuarioN();
        usuarioN.setId(usuarioM.getId());
        usuarioN.setNombre(usuarioM.getNombre());
        usuarioN.setEmail(usuarioM.getEmail());
        usuarioN.setTelefono(usuarioM.getTelefono());
        usuarioN.setDireccion(usuarioM.getDireccion());
        usuarioN.setFotoUrl(usuarioM.getFotoUrl());
        usuarioN.setPassword(usuarioM.getPassword());
        usuarioN.setRol(usuarioM.getRol());
        usuarioN.setActivo(usuarioM.isActivo());
        usuarioN.setFechaReg(usuarioM.getFechaReg() != null ? usuarioM.getFechaReg().toString() : null);
        return usuarioN;
    }

    private UsuarioM cargar(int id, String nombre, String email, String password, String rol,
            String telefono, String direccion, boolean activo) throws SQLException {
        UsuarioM usuario = new UsuarioM();
        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setActivo(activo);
        return usuario;
    }
}
