package com.Smillio.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String sexo;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String tipoSangre;

    @ElementCollection
    @CollectionTable(name = "paciente_alergias", joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "alergia")
    private List<String> alergias = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "paciente_enfermedades", joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "enfermedad")
    private List<String> enfermedades = new ArrayList<>();

    private String avatarColor;
    private LocalDateTime fechaRegistro;
    private String estado; // ACTIVO, NUEVO, RECORDATORIO
    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;
}
