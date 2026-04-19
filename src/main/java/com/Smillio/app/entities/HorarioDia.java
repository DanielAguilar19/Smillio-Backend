package com.Smillio.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "horario_dias")
public class HorarioDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaSemana; // LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    private String horaApertura; // "08:00"
    private String horaCierre;   // "18:00"
    private Boolean cerrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
}
