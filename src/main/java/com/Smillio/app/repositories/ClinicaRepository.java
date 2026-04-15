package com.Smillio.app.repositories;

import com.Smillio.app.entities.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    Optional<Clinica> findByUsuarioId(Long usuarioId);
}
