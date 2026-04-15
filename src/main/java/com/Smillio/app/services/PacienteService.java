package com.Smillio.app.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Smillio.app.dtos.PacienteRequest;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.entities.Usuarios;
import com.Smillio.app.repositories.PacienteRepository;
import com.Smillio.app.repositories.usuariosRepo;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private usuariosRepo usuariosRepo;

    public void crearPerfil(PacienteRequest request) {

        Usuarios usuario = usuariosRepo.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (pacienteRepository.findByUsuarioId(request.getUsuarioId()).isPresent()) {
            throw new RuntimeException("El perfil de paciente ya existe para este usuario");
        }

        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        paciente.setNombre(request.getNombre());
        paciente.setApellido(request.getApellido());
        paciente.setTelefono(request.getTelefono());
        paciente.setGenero(request.getGenero());
        paciente.setEstaActivo(true);

        if (request.getFechaNacimiento() != null && !request.getFechaNacimiento().isBlank()) {
            paciente.setFechaNacimiento(LocalDate.parse(request.getFechaNacimiento()));
        }

        pacienteRepository.save(paciente);
    }
}
