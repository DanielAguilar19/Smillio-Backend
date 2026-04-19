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
@Table(name = "clinicas")
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String rtn;
    private String telefono;
    private String email;
    private String direccion;
    private String web;

    @Column(length = 1000)
    private String descripcion;

    private Double rating;
    private Integer totalResenas;
    private String doctorNombre;
    private String doctorEspecialidad;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios = new ArrayList<>();

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDia> horario = new ArrayList<>();
}
