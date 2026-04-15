package com.Smillio.app.dtos;

import java.time.LocalDate;

public class HistorialClinicoResponse {

    private Long id;
    private Long pacienteId;
    private LocalDate fecha;
    private String descripcion;
    private String tratamiento;
    private String odontologo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getOdontologo() { return odontologo; }
    public void setOdontologo(String odontologo) { this.odontologo = odontologo; }
}
