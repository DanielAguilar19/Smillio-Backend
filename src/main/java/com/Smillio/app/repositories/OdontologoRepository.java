package com.Smillio.app.repositories;

import com.Smillio.app.entities.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {

    Optional<Odontologo> findByUsuarioId(Long usuarioId);

    Optional<Odontologo> findByCedula(String cedula);

    List<Odontologo> findByEstaActivoTrue();

    @Query("SELECT o FROM Odontologo o WHERE o.estaActivo = true AND o.estado IN ('BUSCA_EMPLEO', 'DESEMPLEADO')")
    List<Odontologo> findAvailableOdontologos();

    @Query("SELECT o FROM Odontologo o WHERE o.estaActivo = true AND o.ubicacion LIKE %:ubicacion%")
    List<Odontologo> findByUbicacion(@Param("ubicacion") String ubicacion);

    @Query("SELECT o FROM Odontologo o WHERE o.estaActivo = true AND o.especialidades LIKE %:especialidad%")
    List<Odontologo> findByEspecialidad(@Param("especialidad") String especialidad);

    List<Odontologo> findByClinicaActualId(Long clinicaId);
}
