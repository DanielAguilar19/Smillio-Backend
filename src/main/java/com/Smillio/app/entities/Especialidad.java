package com.Smillio.app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "especialidades")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    private String icono; // nombre del icono PrimeIcons, ej: "pi-tooth"

    @Column(name = "esta_activa")
    private Boolean estaActiva = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }

    public Boolean getEstaActiva() { return estaActiva; }
    public void setEstaActiva(Boolean estaActiva) { this.estaActiva = estaActiva; }
}
