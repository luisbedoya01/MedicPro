package com.example.proyecto_medicpro_grupo2.utiles;

public class Usuario {
    private int idUsuario;
    private String nombresApellidos;
    private String correo;
    private String clave;


    public Usuario(String nombresApellidos, String correo, String clave) {
        this.nombresApellidos = nombresApellidos;
        this.correo = correo;
        this.clave = clave;
    }

    public Usuario(int idUsuario, String nombresApellidos, String correo, String clave) {
        this.idUsuario = idUsuario;
        this.nombresApellidos = nombresApellidos;
        this.correo = correo;
        this.clave = clave;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombresApellidos() {
        return nombresApellidos;
    }

    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
