package com.Smillio.app.repositories;

import com.Smillio.app.entities.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    List<Especialidad> findByEstaActivaTrue();
    Optional<Especialidad> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}
