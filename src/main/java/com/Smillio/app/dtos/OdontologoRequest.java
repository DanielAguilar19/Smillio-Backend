package com.Smillio.app.dtos;

public class OdontologoRequest {

    private Long usuarioId;
    private String nombre;
    private String apellido;
    private String cedula;
    private String especialidades; // comma-separated: "IMPLANTES,ORTODONCIA"
    private String descripcion;
    private String telefono;
    private String ubicacion;
    private String estado; // DESEMPLEADO | BUSCA_EMPLEO | EMPLEADO

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getEspecialidades() { return especialidades; }
    public void setEspecialidades(String especialidades) { this.especialidades = especialidades; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
