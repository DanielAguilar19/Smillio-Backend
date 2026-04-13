package com.Smillio.app.dtos;

public class LoginResponse {

    private Long id;
    private String correo;
    private String rol;

    public LoginResponse(Long id, String correo, String rol) {
        this.id = id;
        this.correo = correo;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }
}
