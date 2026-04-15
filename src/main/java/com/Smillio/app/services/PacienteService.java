package com.Smillio.app.services;

import com.Smillio.app.dtos.PacienteRequest;
import com.Smillio.app.dtos.PacienteResponse;
import com.Smillio.app.entities.Clinica;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.repositories.ClinicaRepository;
import com.Smillio.app.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    public List<PacienteResponse> getAll(Long clinicaId) {
        List<Paciente> pacientes = clinicaId != null
                ? pacienteRepository.findByClinicaId(clinicaId)
                : pacienteRepository.findAll();
        return pacientes.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public PacienteResponse getById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
        return toResponse(paciente);
    }

    public PacienteResponse create(PacienteRequest request) {
        Paciente paciente = new Paciente();
        mapFromRequest(request, paciente);
        paciente.setFechaRegistro(LocalDateTime.now());
        if (paciente.getEstado() == null) paciente.setEstado("NUEVO");
        return toResponse(pacienteRepository.save(paciente));
    }

    public PacienteResponse update(Long id, PacienteRequest request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
        mapFromRequest(request, paciente);
        return toResponse(pacienteRepository.save(paciente));
    }

    public void delete(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    private void mapFromRequest(PacienteRequest request, Paciente paciente) {
        paciente.setNombre(request.getNombre());
        paciente.setApellido(request.getApellido());
        paciente.setSexo(request.getSexo());
        paciente.setEmail(request.getEmail());
        paciente.setTelefono(request.getTelefono());
        paciente.setFechaNacimiento(request.getFechaNacimiento());
        paciente.setTipoSangre(request.getTipoSangre());
        if (request.getAlergias() != null) paciente.setAlergias(request.getAlergias());
        if (request.getEnfermedades() != null) paciente.setEnfermedades(request.getEnfermedades());
        paciente.setAvatarColor(request.getAvatarColor());
        if (request.getEstado() != null) paciente.setEstado(request.getEstado());

        if (request.getClinicaId() != null) {
            Clinica clinica = clinicaRepository.findById(request.getClinicaId())
                    .orElseThrow(() -> new RuntimeException("Clinica no encontrada con id: " + request.getClinicaId()));
            paciente.setClinica(clinica);
        }
    }

    private PacienteResponse toResponse(Paciente p) {
        PacienteResponse r = new PacienteResponse();
        r.setId(p.getId());
        r.setNombre(p.getNombre());
        r.setApellido(p.getApellido());
        r.setSexo(p.getSexo());
        r.setEmail(p.getEmail());
        r.setTelefono(p.getTelefono());
        r.setFechaNacimiento(p.getFechaNacimiento());
        r.setTipoSangre(p.getTipoSangre());
        r.setAlergias(p.getAlergias());
        r.setEnfermedades(p.getEnfermedades());
        r.setAvatarColor(p.getAvatarColor());
        r.setFechaRegistro(p.getFechaRegistro());
        r.setEstado(p.getEstado());
        r.setRating(p.getRating());
        if (p.getClinica() != null) r.setClinicaId(p.getClinica().getId());
        return r;
    }
}
