package com.Smillio.app.repositories;

import com.Smillio.app.entities.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Long> {
    List<HistorialClinico> findByPacienteIdOrderByFechaDesc(Long pacienteId);
}
