package com.Smillio.app.dtos;

import jakarta.validation.constraints.NotBlank;

public class ResponderResenaRequest {

    @NotBlank
    private String respuesta;

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
}
