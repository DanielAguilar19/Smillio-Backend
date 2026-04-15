package com.Smillio.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Smillio.app.dtos.PacienteRequest;
import com.Smillio.app.services.PacienteService;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin("*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/perfil")
    public ResponseEntity<?> crearPerfil(@RequestBody PacienteRequest request) {
        try {
            pacienteService.crearPerfil(request);
            return ResponseEntity.ok("Perfil de paciente creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
