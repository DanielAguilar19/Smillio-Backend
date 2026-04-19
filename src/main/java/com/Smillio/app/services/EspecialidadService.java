package com.Smillio.app.services;

import com.Smillio.app.dtos.EspecialidadRequest;
import com.Smillio.app.entities.Especialidad;
import com.Smillio.app.repositories.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public List<Especialidad> listarActivas() {
        return especialidadRepository.findByEstaActivaTrue();
    }

    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }

    public Especialidad crear(EspecialidadRequest request) {
        if (especialidadRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RuntimeException("Ya existe una especialidad con ese nombre");
        }
        Especialidad e = new Especialidad();
        e.setNombre(request.getNombre());
        e.setDescripcion(request.getDescripcion());
        e.setIcono(request.getIcono());
        e.setEstaActiva(true);
        return especialidadRepository.save(e);
    }

    public Especialidad actualizar(Long id, EspecialidadRequest request) {
        Especialidad e = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        e.setNombre(request.getNombre());
        if (request.getDescripcion() != null) e.setDescripcion(request.getDescripcion());
        if (request.getIcono() != null) e.setIcono(request.getIcono());
        return especialidadRepository.save(e);
    }

    public void eliminar(Long id) {
        Especialidad e = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        e.setEstaActiva(false);
        especialidadRepository.save(e);
    }
}
