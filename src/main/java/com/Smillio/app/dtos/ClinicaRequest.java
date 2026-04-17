package com.Smillio.app.dtos;

public class ClinicaRequest {

    private Long usuarioId; // required when admin creates a clinic account

    private String nombre;
    private String rtn;
    private String telefono;
    private String email;
    private String direccion;
    private String web;
    private String descripcion;
    private String doctorNombre;
    private String doctorEspecialidad;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRtn() { return rtn; }
    public void setRtn(String rtn) { this.rtn = rtn; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getWeb() { return web; }
    public void setWeb(String web) { this.web = web; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDoctorNombre() { return doctorNombre; }
    public void setDoctorNombre(String doctorNombre) { this.doctorNombre = doctorNombre; }

    public String getDoctorEspecialidad() { return doctorEspecialidad; }
    public void setDoctorEspecialidad(String doctorEspecialidad) { this.doctorEspecialidad = doctorEspecialidad; }
}
