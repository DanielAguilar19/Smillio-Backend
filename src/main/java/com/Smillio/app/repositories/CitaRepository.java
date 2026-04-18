package com.Smillio.app.repositories;

import com.Smillio.app.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByClinicaId(Long clinicaId);
    List<Cita> findByClinicaIdAndFecha(Long clinicaId, LocalDate fecha);
    List<Cita> findByPacienteId(Long pacienteId);
    List<Cita> findByClinicaIdOrderByFechaAscHoraAsc(Long clinicaId);
    List<Cita> findByOdontologoId(Long odontologoId);
}
