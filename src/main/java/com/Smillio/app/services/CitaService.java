package com.Smillio.app.services;

import com.Smillio.app.dtos.CitaRequest;
import com.Smillio.app.dtos.CitaResponse;
import com.Smillio.app.entities.Cita;
import com.Smillio.app.entities.Clinica;
import com.Smillio.app.entities.Odontologo;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.repositories.CitaRepository;
import com.Smillio.app.repositories.ClinicaRepository;
import com.Smillio.app.repositories.OdontologoRepository;
import com.Smillio.app.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired private CitaRepository citaRepository;
    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private ClinicaRepository clinicaRepository;
    @Autowired private OdontologoRepository odontologoRepository;

    // ── Crear cita ──────────────────────────────────────────────────
    public CitaResponse crearCita(CitaRequest request) {
        Paciente paciente = resolverPaciente(request);

        Clinica clinica = clinicaRepository.findById(request.getClinicaId())
                .orElseThrow(() -> new RuntimeException("Clínica no encontrada"));

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setClinica(clinica);
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setServicio(request.getServicio());
        cita.setDuracion(request.getDuracion() != null ? request.getDuracion() : 30);
        cita.setEstado(request.getEstado() != null ? request.getEstado() : "CONFIRMADA");
        cita.setNotas(request.getNotas());

        if (request.getOdontologoId() != null) {
            Odontologo od = odontologoRepository.findById(request.getOdontologoId())
                    .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado"));
            cita.setOdontologo(od);
        }

        return toResponse(citaRepository.save(cita));
    }

    // ── Obtener por ID ─────────────────────────────────────────────
    public CitaResponse obtenerCita(Long id) {
        return toResponse(citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada")));
    }

    // ── Citas de un paciente (por Paciente.id) ─────────────────────
    public List<CitaResponse> obtenerCitasPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Citas de un paciente buscado por su usuarioId ──────────────
    public List<CitaResponse> obtenerCitasPorUsuario(Long usuarioId) {
        Paciente paciente = pacienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil de paciente no encontrado para este usuario"));
        return citaRepository.findByPacienteId(paciente.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Citas de una clínica ───────────────────────────────────────
    public List<CitaResponse> obtenerCitasClinica(Long clinicaId) {
        return citaRepository.findByClinicaIdOrderByFechaAscHoraAsc(clinicaId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Citas de un odontólogo ─────────────────────────────────────
    public List<CitaResponse> obtenerCitasOdontologo(Long odontologoId) {
        return citaRepository.findByOdontologoId(odontologoId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Actualizar estado ──────────────────────────────────────────
    public CitaResponse actualizarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        return toResponse(citaRepository.save(cita));
    }

    // ── Reagendar ─────────────────────────────────────────────────
    public CitaResponse reagendar(Long id, LocalDate nuevaFecha, LocalTime nuevaHora) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        if ("CANCELADA".equals(cita.getEstado()) || "COMPLETADA".equals(cita.getEstado())) {
            throw new RuntimeException("No se puede reagendar una cita " + cita.getEstado().toLowerCase());
        }
        cita.setFecha(nuevaFecha);
        cita.setHora(nuevaHora);
        cita.setEstado("CONFIRMADA");
        return toResponse(citaRepository.save(cita));
    }

    // ── Delegar a otro odontólogo ──────────────────────────────────
    public CitaResponse delegar(Long citaId, Long nuevoOdontologoId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        Odontologo nuevo = odontologoRepository.findById(nuevoOdontologoId)
                .orElseThrow(() -> new RuntimeException("Odontólogo destino no encontrado"));
        cita.setOdontologo(nuevo);
        return toResponse(citaRepository.save(cita));
    }

    // ── Cancelar ─────────────────────────────────────────────────
    public void cancelarCita(Long id) {
        actualizarEstado(id, "CANCELADA");
    }

    // ── Privados ──────────────────────────────────────────────────
    private Paciente resolverPaciente(CitaRequest request) {
        // Preferir pacienteId directo; si no, buscar por usuarioId
        if (request.getPacienteId() != null) {
            return pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + request.getPacienteId()));
        }
        if (request.getUsuarioId() != null) {
            return pacienteRepository.findByUsuarioId(request.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Perfil de paciente no encontrado para el usuario " + request.getUsuarioId()));
        }
        throw new RuntimeException("Se requiere pacienteId o usuarioId");
    }

    private CitaResponse toResponse(Cita c) {
        CitaResponse r = new CitaResponse();
        r.setId(c.getId());
        if (c.getPaciente() != null) {
            r.setPacienteId(c.getPaciente().getId());
            r.setPacienteNombre(c.getPaciente().getNombre() + " " +
                    (c.getPaciente().getApellido() != null ? c.getPaciente().getApellido() : ""));
        }
        if (c.getClinica() != null) {
            r.setClinicaId(c.getClinica().getId());
            r.setClinicaNombre(c.getClinica().getNombre());
        }
        if (c.getOdontologo() != null) {
            r.setOdontologoId(c.getOdontologo().getId());
            r.setOdontologoNombre(c.getOdontologo().getNombre() + " " +
                    (c.getOdontologo().getApellido() != null ? c.getOdontologo().getApellido() : ""));
        }
        r.setFecha(c.getFecha());
        r.setHora(c.getHora());
        r.setServicio(c.getServicio());
        r.setDuracion(c.getDuracion());
        r.setEstado(c.getEstado());
        r.setNotas(c.getNotas());
        return r;
    }
}
