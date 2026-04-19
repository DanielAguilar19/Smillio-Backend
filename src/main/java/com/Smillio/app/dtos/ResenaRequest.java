package com.Smillio.app.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ResenaRequest {

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long clinicaId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    private String texto;

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getClinicaId() { return clinicaId; }
    public void setClinicaId(Long clinicaId) { this.clinicaId = clinicaId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}
