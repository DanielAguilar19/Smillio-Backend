package com.Smillio.app.services;

import com.Smillio.app.dtos.*;
import com.Smillio.app.entities.Clinica;
import com.Smillio.app.entities.HorarioDia;
import com.Smillio.app.entities.Servicio;
import com.Smillio.app.repositories.ClinicaRepository;
import com.Smillio.app.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public ClinicaResponse getByUsuario(Long usuarioId) {
        Clinica clinica = clinicaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Clínica no encontrada para este usuario"));
        return toResponse(clinica);
    }

    public List<ClinicaResponse> getAll() {
        return clinicaRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ClinicaResponse getById(Long id) {
        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinica no encontrada con id: " + id));
        return toResponse(clinica);
    }

    public ClinicaResponse update(Long id, ClinicaRequest request) {
        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinica no encontrada con id: " + id));
        if (request.getNombre() != null) clinica.setNombre(request.getNombre());
        if (request.getRtn() != null) clinica.setRtn(request.getRtn());
        if (request.getTelefono() != null) clinica.setTelefono(request.getTelefono());
        if (request.getEmail() != null) clinica.setEmail(request.getEmail());
        if (request.getDireccion() != null) clinica.setDireccion(request.getDireccion());
        if (request.getWeb() != null) clinica.setWeb(request.getWeb());
        if (request.getDescripcion() != null) clinica.setDescripcion(request.getDescripcion());
        if (request.getDoctorNombre() != null) clinica.setDoctorNombre(request.getDoctorNombre());
        if (request.getDoctorEspecialidad() != null) clinica.setDoctorEspecialidad(request.getDoctorEspecialidad());
        return toResponse(clinicaRepository.save(clinica));
    }

    public List<ServicioResponse> getServicios(Long clinicaId) {
        return servicioRepository.findByClinicaId(clinicaId)
                .stream().map(this::toServicioResponse).collect(Collectors.toList());
    }

    public ServicioResponse addServicio(Long clinicaId, ServicioRequest request) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RuntimeException("Clinica no encontrada con id: " + clinicaId));
        Servicio servicio = new Servicio();
        servicio.setNombre(request.getNombre());
        servicio.setDuracion(request.getDuracion());
        servicio.setPrecio(request.getPrecio());
        servicio.setClinica(clinica);
        return toServicioResponse(servicioRepository.save(servicio));
    }

    public ServicioResponse updateServicio(Long clinicaId, Long servicioId, ServicioRequest request) {
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + servicioId));
        if (!servicio.getClinica().getId().equals(clinicaId)) {
            throw new RuntimeException("El servicio no pertenece a esta clinica");
        }
        servicio.setNombre(request.getNombre());
        servicio.setDuracion(request.getDuracion());
        servicio.setPrecio(request.getPrecio());
        return toServicioResponse(servicioRepository.save(servicio));
    }

    public void deleteServicio(Long clinicaId, Long servicioId) {
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + servicioId));
        if (!servicio.getClinica().getId().equals(clinicaId)) {
            throw new RuntimeException("El servicio no pertenece a esta clinica");
        }
        servicioRepository.deleteById(servicioId);
    }

    public ClinicaResponse updateHorario(Long clinicaId, List<HorarioDiaRequest> horarioRequest) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RuntimeException("Clinica no encontrada con id: " + clinicaId));
        clinica.getHorario().clear();
        for (HorarioDiaRequest h : horarioRequest) {
            HorarioDia dia = new HorarioDia();
            dia.setDiaSemana(h.getDiaSemana());
            dia.setHoraApertura(h.getHoraApertura());
            dia.setHoraCierre(h.getHoraCierre());
            dia.setCerrado(h.getCerrado() != null ? h.getCerrado() : false);
            dia.setClinica(clinica);
            clinica.getHorario().add(dia);
        }
        return toResponse(clinicaRepository.save(clinica));
    }

    private ClinicaResponse toResponse(Clinica c) {
        ClinicaResponse r = new ClinicaResponse();
        r.setId(c.getId());
        r.setNombre(c.getNombre());
        r.setRtn(c.getRtn());
        r.setTelefono(c.getTelefono());
        r.setEmail(c.getEmail());
        r.setDireccion(c.getDireccion());
        r.setWeb(c.getWeb());
        r.setDescripcion(c.getDescripcion());
        r.setRating(c.getRating());
        r.setTotalResenas(c.getTotalResenas());
        r.setDoctorNombre(c.getDoctorNombre());
        r.setDoctorEspecialidad(c.getDoctorEspecialidad());
        r.setServicios(c.getServicios().stream().map(this::toServicioResponse).collect(Collectors.toList()));
        r.setHorario(c.getHorario().stream().map(this::toHorarioResponse).collect(Collectors.toList()));
        return r;
    }

    private ServicioResponse toServicioResponse(Servicio s) {
        ServicioResponse r = new ServicioResponse();
        r.setId(s.getId());
        r.setNombre(s.getNombre());
        r.setDuracion(s.getDuracion());
        r.setPrecio(s.getPrecio());
        return r;
    }

    private HorarioDiaResponse toHorarioResponse(HorarioDia h) {
        HorarioDiaResponse r = new HorarioDiaResponse();
        r.setId(h.getId());
        r.setDiaSemana(h.getDiaSemana());
        r.setHoraApertura(h.getHoraApertura());
        r.setHoraCierre(h.getHoraCierre());
        r.setCerrado(h.getCerrado());
        return r;
    }
}
