package com.Smillio.app.dtos;

import jakarta.validation.constraints.NotBlank;

public class EspecialidadRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    private String icono;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
}
