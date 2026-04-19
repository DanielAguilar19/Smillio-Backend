package com.Smillio.app.services;

import com.Smillio.app.dtos.HistorialClinicoRequest;
import com.Smillio.app.dtos.HistorialClinicoResponse;
import com.Smillio.app.entities.HistorialClinico;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.repositories.HistorialClinicoRepository;
import com.Smillio.app.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialClinicoService {

    @Autowired
    private HistorialClinicoRepository historialClinicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public HistorialClinicoResponse crearRegistro(HistorialClinicoRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        HistorialClinico registro = new HistorialClinico();
        registro.setPaciente(paciente);
        registro.setFecha(request.getFecha() != null ? request.getFecha() : LocalDate.now());
        registro.setDescripcion(request.getDescripcion());
        registro.setTratamiento(request.getTratamiento());
        registro.setOdontologo(request.getOdontologo());

        HistorialClinico guardado = historialClinicoRepository.save(registro);
        return toResponse(guardado);
    }

    public List<HistorialClinicoResponse> obtenerHistorialPaciente(Long pacienteId) {
        return historialClinicoRepository.findByPacienteIdOrderByFechaDesc(pacienteId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public HistorialClinicoResponse obtenerRegistro(Long id) {
        HistorialClinico registro = historialClinicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de historial clínico no encontrado"));
        return toResponse(registro);
    }

    public HistorialClinicoResponse actualizarRegistro(Long id, HistorialClinicoRequest request) {
        HistorialClinico registro = historialClinicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de historial clínico no encontrado"));

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        registro.setPaciente(paciente);
        registro.setFecha(request.getFecha() != null ? request.getFecha() : registro.getFecha());
        registro.setDescripcion(request.getDescripcion());
        registro.setTratamiento(request.getTratamiento());
        registro.setOdontologo(request.getOdontologo());

        return toResponse(historialClinicoRepository.save(registro));
    }

    private HistorialClinicoResponse toResponse(HistorialClinico registro) {
        HistorialClinicoResponse response = new HistorialClinicoResponse();
        response.setId(registro.getId());
        response.setPacienteId(registro.getPaciente().getId());
        response.setFecha(registro.getFecha());
        response.setDescripcion(registro.getDescripcion());
        response.setTratamiento(registro.getTratamiento());
        response.setOdontologo(registro.getOdontologo());
        return response;
    }
}
