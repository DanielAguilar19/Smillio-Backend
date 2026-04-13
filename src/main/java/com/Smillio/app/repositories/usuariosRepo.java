package com.Smillio.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Smillio.app.entities.Usuarios;

@Repository
public interface usuariosRepo extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByCorreoElectronico(String correoElectronico);
}
