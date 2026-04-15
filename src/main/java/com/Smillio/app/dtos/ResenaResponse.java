package com.Smillio.app.dtos;

import java.time.LocalDate;

public class ResenaResponse {

    private Long id;
    private Long pacienteId;
    private String pacienteNombre;
    private Long clinicaId;
    private LocalDate fecha;
    private Integer rating;
    private String texto;
    private String respuesta;
    private Boolean respondida;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }

    public Long getClinicaId() { return clinicaId; }
    public void setClinicaId(Long clinicaId) { this.clinicaId = clinicaId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public Boolean getRespondida() { return respondida; }
    public void setRespondida(Boolean respondida) { this.respondida = respondida; }
}
