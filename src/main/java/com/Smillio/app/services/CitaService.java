package com.Smillio.app.services;

import com.Smillio.app.dtos.CitaRequest;
import com.Smillio.app.dtos.CitaResponse;
import com.Smillio.app.entities.Cita;
import com.Smillio.app.entities.Clinica;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.repositories.CitaRepository;
import com.Smillio.app.repositories.ClinicaRepository;
import com.Smillio.app.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    public CitaResponse crearCita(CitaRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Clinica clinica = clinicaRepository.findById(request.getClinicaId())
                .orElseThrow(() -> new RuntimeException("Clínica no encontrada"));

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setClinica(clinica);
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setServicio(request.getServicio());
        cita.setDuracion(request.getDuracion());
        cita.setEstado("CONFIRMADA");
        cita.setNotas(request.getNotas());

        Cita guardada = citaRepository.save(cita);
        return toResponse(guardada);
    }

    public CitaResponse obtenerCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return toResponse(cita);
    }

    public List<CitaResponse> obtenerCitasPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<CitaResponse> obtenerCitasClinica(Long clinicaId) {
        return citaRepository.findByClinicaId(clinicaId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CitaResponse actualizarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        return toResponse(citaRepository.save(cita));
    }

    public void cancelarCita(Long id) {
        actualizarEstado(id, "CANCELADA");
    }

    private CitaResponse toResponse(Cita cita) {
        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setPacienteId(cita.getPaciente().getId());
        response.setClinicaId(cita.getClinica().getId());
        response.setFecha(cita.getFecha());
        response.setHora(cita.getHora());
        response.setServicio(cita.getServicio());
        response.setDuracion(cita.getDuracion());
        response.setEstado(cita.getEstado());
        response.setNotas(cita.getNotas());
        return response;
    }
}
