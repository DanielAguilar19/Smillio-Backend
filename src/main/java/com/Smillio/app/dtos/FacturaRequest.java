package com.Smillio.app.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FacturaRequest {

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long clinicaId;

    private LocalDate fecha;
    private String servicio;

    @NotNull
    private Double monto;

    private String estado;
    private String metodoPago;

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getClinicaId() { return clinicaId; }
    public void setClinicaId(Long clinicaId) { this.clinicaId = clinicaId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
