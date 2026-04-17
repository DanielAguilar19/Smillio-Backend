package com.Smillio.app.repositories;

import com.Smillio.app.entities.ClinicaOdontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicaOdontologoRepository extends JpaRepository<ClinicaOdontologo, Long> {
    List<ClinicaOdontologo> findByClinicaId(Long clinicaId);
    List<ClinicaOdontologo> findByClinicaIdAndEstado(Long clinicaId, String estado);
    List<ClinicaOdontologo> findByOdontologoId(Long odontologoId);
    Optional<ClinicaOdontologo> findByClinicaIdAndOdontologoId(Long clinicaId, Long odontologoId);
    boolean existsByClinicaIdAndOdontologoId(Long clinicaId, Long odontologoId);
}
