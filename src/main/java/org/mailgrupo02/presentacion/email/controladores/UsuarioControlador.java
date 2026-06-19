package org.mailgrupo02.presentacion.email.controladores;

import org.mailgrupo02.datos.modelo.UsuarioM;
import org.mailgrupo02.negocio.usuarios.UsuarioService;
import org.mailgrupo02.negocio.usuarios.UsuarioN;
import org.mailgrupo02.presentacion.email.PUsuarios;

import java.util.List;

public class UsuarioControlador {

    public static boolean canHandle(String cmd) {
        if (cmd == null) return false;
        switch (cmd.toUpperCase()) {
            case "LISTARUSUARIOS": case "LISTARUSUARIO":
            case "CREATEUSUARIO":
            case "UPDATEUSUARIO":
            case "DELETEUSUARIO":
            case "GETUSUARIO":
                return true;
            default:
                return false;
        }
    }

    public static String handle(String cmd, List<String> params) {
        try {
            UsuarioService service = new UsuarioService(new UsuarioM());
            String rawResult;

            switch (cmd.toUpperCase()) {
                case "LISTARUSUARIOS":
                case "LISTARUSUARIO":
                    rawResult = service.obtenerUsuarios();
                    break;

                case "GETUSUARIO": {
                    if (params.isEmpty()) return PUsuarios.generarHtml(cmd, "Error: se requiere el ID del usuario.");
                    int id = Integer.parseInt(params.get(0).trim());
                    UsuarioN u = service.leerUsuario(id);
                    rawResult = "=== DATOS DEL USUARIO ===\r\n\r\n" +
                                "ID:           " + u.getId()        + "\r\n" +
                                "Nombre:       " + u.getNombre()    + "\r\n" +
                                "Email:        " + u.getEmail()     + "\r\n" +
                                "Rol:          " + u.getRol()       + "\r\n" +
                                "Teléfono:     " + nvl(u.getTelefono())  + "\r\n" +
                                "Dirección:    " + nvl(u.getDireccion()) + "\r\n" +
                                "Activo:       " + (u.isActivo() ? "Sí" : "No") + "\r\n" +
                                "Fecha Reg.:   " + nvl(u.getFechaReg());
                    break;
                }

                case "CREATEUSUARIO":
                    if (params.size() < 6) return PUsuarios.generarHtml(cmd, "Error: se requieren 6 parámetros [nombre,email,password,rol,telefono,direccion].");
                    rawResult = service.agregarUsuario(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4), params.get(5));
                    break;

                case "UPDATEUSUARIO":
                    if (params.size() < 8) return PUsuarios.generarHtml(cmd, "Error: se requieren 8 parámetros [id,nombre,email,password,rol,telefono,direccion,activo].");
                    rawResult = service.actualizarUsuario(
                        Integer.parseInt(params.get(0).trim()), params.get(1), params.get(2),
                        params.get(3), params.get(4), params.get(5), params.get(6),
                        Boolean.parseBoolean(params.get(7).trim()));
                    break;

                case "DELETEUSUARIO":
                    if (params.isEmpty()) return PUsuarios.generarHtml(cmd, "Error: se requiere el ID del usuario.");
                    rawResult = service.eliminarUsuario(Integer.parseInt(params.get(0).trim()));
                    break;

                default:
                    rawResult = "Error: Comando de usuarios no soportado.";
            }

            return PUsuarios.generarHtml(cmd, rawResult);
        } catch (Exception e) {
            return PUsuarios.generarHtml(cmd, "Error: " + e.getMessage());
        }
    }

    private static String nvl(String val) {
        return val != null ? val : "N/A";
    }
}
