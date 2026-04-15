package com.Smillio.app.repositories;

import com.Smillio.app.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByClinicaId(Long clinicaId);
    List<Paciente> findByClinicaIdAndEstado(Long clinicaId, String estado);
    boolean existsByEmail(String email);
}
