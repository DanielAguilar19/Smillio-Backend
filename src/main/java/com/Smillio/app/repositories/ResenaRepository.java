package com.Smillio.app.repositories;

import com.Smillio.app.entities.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByClinicaId(Long clinicaId);
    List<Resena> findByClinicaIdAndRespondida(Long clinicaId, Boolean respondida);
}
