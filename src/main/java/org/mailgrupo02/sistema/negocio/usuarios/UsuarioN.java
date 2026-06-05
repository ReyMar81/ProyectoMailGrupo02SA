package org.mailgrupo02.sistema.negocio.usuarios;

import java.io.Serializable;

public class UsuarioN implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String fotoUrl;
    private String password;
    private String rol;
    private boolean activo;
    private String fechaReg;

    public UsuarioN() {
    }

    public UsuarioN(int id, String nombre, String email, String telefono,
            String direccion, String fotoUrl, String password, String rol,
            boolean activo, String fechaReg) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fotoUrl = fotoUrl;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
        this.fechaReg = fechaReg;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public String getFechaReg() { return fechaReg; }
    public void setFechaReg(String fechaReg) { this.fechaReg = fechaReg; }

    @Override
    public String toString() {
        return "UsuarioN{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fotoUrl='" + fotoUrl + '\'' +
                ", password='" + password + '\'' +
                ", rol='" + rol + '\'' +
                ", activo=" + activo +
                ", fechaReg='" + fechaReg + '\'' +
                '}';
    }
}
