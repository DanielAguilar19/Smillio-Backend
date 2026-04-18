package com.Smillio.app.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaResponse {

    private Long id;
    private Long pacienteId;
    private String pacienteNombre;
    private Long clinicaId;
    private String clinicaNombre;
    private String clinicaDireccion;
    private Long odontologoId;
    private String odontologoNombre;
    private LocalDate fecha;
    private LocalTime hora;
    private String servicio;
    private Integer duracion;
    private String estado;
    private String notas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }

    public Long getClinicaId() { return clinicaId; }
    public void setClinicaId(Long clinicaId) { this.clinicaId = clinicaId; }

    public String getClinicaNombre() { return clinicaNombre; }
    public void setClinicaNombre(String clinicaNombre) { this.clinicaNombre = clinicaNombre; }

    public String getClinicaDireccion() { return clinicaDireccion; }
    public void setClinicaDireccion(String clinicaDireccion) { this.clinicaDireccion = clinicaDireccion; }

    public Long getOdontologoId() { return odontologoId; }
    public void setOdontologoId(Long odontologoId) { this.odontologoId = odontologoId; }

    public String getOdontologoNombre() { return odontologoNombre; }
    public void setOdontologoNombre(String odontologoNombre) { this.odontologoNombre = odontologoNombre; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
