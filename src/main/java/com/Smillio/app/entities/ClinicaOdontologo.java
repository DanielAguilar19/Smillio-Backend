package com.Smillio.app.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clinica_odontologos",
       uniqueConstraints = @UniqueConstraint(columnNames = {"clinica_id", "odontologo_id"}))
public class ClinicaOdontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Odontologo odontologo;

    // CONTRATADO = hired, PROSPECTO = interested/pending
    @Column(nullable = false)
    private String estado = "PROSPECTO";

    private LocalDate fechaInicio;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Clinica getClinica() { return clinica; }
    public void setClinica(Clinica clinica) { this.clinica = clinica; }

    public Odontologo getOdontologo() { return odontologo; }
    public void setOdontologo(Odontologo odontologo) { this.odontologo = odontologo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
}
