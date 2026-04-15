package com.Smillio.app.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public class PacienteRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    private String sexo;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String tipoSangre;
    private List<String> alergias;
    private List<String> enfermedades;
    private String avatarColor;
    private String estado;
    private Long clinicaId;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }

    public List<String> getAlergias() { return alergias; }
    public void setAlergias(List<String> alergias) { this.alergias = alergias; }

    public List<String> getEnfermedades() { return enfermedades; }
    public void setEnfermedades(List<String> enfermedades) { this.enfermedades = enfermedades; }

    public String getAvatarColor() { return avatarColor; }
    public void setAvatarColor(String avatarColor) { this.avatarColor = avatarColor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getClinicaId() { return clinicaId; }
    public void setClinicaId(Long clinicaId) { this.clinicaId = clinicaId; }
}
