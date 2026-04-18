package com.Smillio.app.services;

import com.Smillio.app.dtos.ResenaRequest;
import com.Smillio.app.dtos.ResenaResponse;
import com.Smillio.app.dtos.ResponderResenaRequest;
import com.Smillio.app.entities.Clinica;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.entities.Resena;
import com.Smillio.app.repositories.ClinicaRepository;
import com.Smillio.app.repositories.PacienteRepository;
import com.Smillio.app.repositories.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    public ResenaResponse crearResena(ResenaRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Clinica clinica = clinicaRepository.findById(request.getClinicaId())
                .orElseThrow(() -> new RuntimeException("Clínica no encontrada"));

        Resena resena = new Resena();
        resena.setPaciente(paciente);
        resena.setClinica(clinica);
        resena.setFecha(LocalDate.now());
        resena.setRating(request.getRating());
        resena.setTexto(request.getTexto());
        resena.setRespondida(false);

        Resena guardada = resenaRepository.save(resena);

        actualizarRatingClinica(clinica);

        return toResponse(guardada);
    }

    public List<ResenaResponse> obtenerResenasClinica(Long clinicaId) {
        return resenaRepository.findByClinicaId(clinicaId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<ResenaResponse> obtenerResenasPendientes(Long clinicaId) {
        return resenaRepository.findByClinicaIdAndRespondida(clinicaId, false)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ResenaResponse responderResena(Long id, ResponderResenaRequest request) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));

        resena.setRespuesta(request.getRespuesta());
        resena.setRespondida(true);

        return toResponse(resenaRepository.save(resena));
    }

    public Map<String, Object> obtenerEstadisticas(Long clinicaId) {
        List<Resena> resenas = resenaRepository.findByClinicaId(clinicaId);

        Map<String, Object> estadisticas = new HashMap<>();

        int totalResenas = resenas.size();
        double promedioRating = resenas.stream()
                .mapToInt(Resena::getRating)
                .average()
                .orElse(0.0);

        Map<Integer, Long> distribucion = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            int star = i;
            long count = resenas.stream()
                    .filter(r -> r.getRating() == star)
                    .count();
            distribucion.put(star, count);
        }

        estadisticas.put("promedioRating", Math.round(promedioRating * 10.0) / 10.0);
        estadisticas.put("totalResenas", totalResenas);
        estadisticas.put("distribucion", distribucion);

        return estadisticas;
    }

    private void actualizarRatingClinica(Clinica clinica) {
        List<Resena> resenas = resenaRepository.findByClinicaId(clinica.getId());

        int total = resenas.size();
        double promedio = resenas.stream()
                .mapToInt(Resena::getRating)
                .average()
                .orElse(0.0);

        clinica.setTotalResenas(total);
        clinica.setRating(Math.round(promedio * 10.0) / 10.0);
        clinicaRepository.save(clinica);
    }

    private ResenaResponse toResponse(Resena resena) {
        ResenaResponse response = new ResenaResponse();
        response.setId(resena.getId());
        response.setPacienteId(resena.getPaciente().getId());
        response.setPacienteNombre(resena.getPaciente().getNombre());
        response.setClinicaId(resena.getClinica().getId());
        response.setFecha(resena.getFecha());
        response.setRating(resena.getRating());
        response.setTexto(resena.getTexto());
        response.setRespuesta(resena.getRespuesta());
        response.setRespondida(resena.getRespondida());
        return response;
    }
}
