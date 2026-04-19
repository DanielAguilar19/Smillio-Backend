package com.Smillio.app.dtos;

import java.util.List;

public class ClinicaResponse {

    private Long id;
    private String nombre;
    private String rtn;
    private String telefono;
    private String email;
    private String direccion;
    private String web;
    private String descripcion;
    private Double rating;
    private Integer totalResenas;
    private String doctorNombre;
    private String doctorEspecialidad;
    private List<ServicioResponse> servicios;
    private List<HorarioDiaResponse> horario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getTotalResenas() { return totalResenas; }
    public void setTotalResenas(Integer totalResenas) { this.totalResenas = totalResenas; }

    public String getDoctorNombre() { return doctorNombre; }
    public void setDoctorNombre(String doctorNombre) { this.doctorNombre = doctorNombre; }

    public String getDoctorEspecialidad() { return doctorEspecialidad; }
    public void setDoctorEspecialidad(String doctorEspecialidad) { this.doctorEspecialidad = doctorEspecialidad; }

    public List<ServicioResponse> getServicios() { return servicios; }
    public void setServicios(List<ServicioResponse> servicios) { this.servicios = servicios; }

    public List<HorarioDiaResponse> getHorario() { return horario; }
    public void setHorario(List<HorarioDiaResponse> horario) { this.horario = horario; }
}
