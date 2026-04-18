package com.Smillio.app.repositories;

import com.Smillio.app.entities.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    Optional<Clinica> findByUsuarioId(Long usuarioId);

    // Search clinics by specialty: match clinica.doctorEspecialidad OR any hired dentist's especialidades
    @Query("SELECT DISTINCT c FROM Clinica c " +
           "LEFT JOIN ClinicaOdontologo co ON co.clinica.id = c.id AND co.estado = 'CONTRATADO' " +
           "LEFT JOIN co.odontologo od " +
           "WHERE LOWER(c.doctorEspecialidad) LIKE LOWER(CONCAT('%', :especialidad, '%')) " +
           "OR LOWER(od.especialidades) LIKE LOWER(CONCAT('%', :especialidad, '%'))")
    List<Clinica> findByEspecialidad(@Param("especialidad") String especialidad);
}
