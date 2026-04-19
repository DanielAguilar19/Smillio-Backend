package com.Smillio.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuarios usuario;

    @Column(nullable = false)
    private String nombre;

    private String apellido;

    @Column(unique = true, nullable = false)
    private String cedula;

    // Especialidades: "IMPLANTES,ORTODONCIA,ENDODONCIA"
    private String especialidades;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "estado")
    private String estado; // DESEMPLEADO, BUSCA_EMPLEO, EMPLEADO

    private String telefono;
    private String ubicacion;

    private Double rating;
    private Integer totalResenas;

    @Column(name = "esta_activo")
    private Boolean estaActivo = true;

    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoOdontologo> documentos = new ArrayList<>();

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "clinica_id")
    private Clinica clinicaActual;
}
