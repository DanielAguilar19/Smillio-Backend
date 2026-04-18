package com.Smillio.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Smillio.app.dtos.PacienteRequest;
import com.Smillio.app.entities.Cita;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.entities.Usuarios;
import com.Smillio.app.repositories.CitaRepository;
import com.Smillio.app.repositories.PacienteRepository;
import com.Smillio.app.repositories.usuariosRepo;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private usuariosRepo usuariosRepo;

    @Autowired
    private CitaRepository citaRepository;

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

    // Obtener paciente por ID
    public Paciente obtenerPaciente(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    // Obtener paciente por usuario ID
    public Paciente obtenerPacientePorUsuario(Long usuarioId) {
        return pacienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado para este usuario"));
    }

    // Obtener todos los pacientes activos
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll().stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaActivo()))
                .collect(Collectors.toList());
    }

    // Obtener pacientes únicos de una clínica (a través de citas)
    public List<Paciente> obtenerPacientesClinica(Long clinicaId) {
        List<Cita> citas = citaRepository.findByClinicaId(clinicaId);
        return citas.stream()
                .map(Cita::getPaciente)
                .distinct()
                .filter(p -> Boolean.TRUE.equals(p.getEstaActivo()))
                .collect(Collectors.toList());
    }

    // Actualizar paciente
    public Paciente actualizarPaciente(Long id, PacienteRequest datos) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (datos.getNombre() != null) {
            paciente.setNombre(datos.getNombre());
        }
        if (datos.getApellido() != null) {
            paciente.setApellido(datos.getApellido());
        }
        if (datos.getTelefono() != null) {
            paciente.setTelefono(datos.getTelefono());
        }
        if (datos.getGenero() != null) {
            paciente.setGenero(datos.getGenero());
        }
        if (datos.getFechaNacimiento() != null && !datos.getFechaNacimiento().isBlank()) {
            paciente.setFechaNacimiento(LocalDate.parse(datos.getFechaNacimiento()));
        }

        return pacienteRepository.save(paciente);
    }
}
