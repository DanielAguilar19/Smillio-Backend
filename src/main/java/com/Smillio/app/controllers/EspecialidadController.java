package com.Smillio.app.controllers;

import com.Smillio.app.entities.Especialidad;
import com.Smillio.app.services.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin("*")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Public endpoint: returns only active specialties (used on homepage categories)
    @GetMapping
    public ResponseEntity<List<Especialidad>> listarActivas() {
        return ResponseEntity.ok(especialidadService.listarActivas());
    }
}
