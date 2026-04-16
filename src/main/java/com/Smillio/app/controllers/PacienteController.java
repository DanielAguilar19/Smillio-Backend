package com.Smillio.app.controllers;

import com.Smillio.app.dtos.PacienteRequest;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pacienteService.obtenerPaciente(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(pacienteService.obtenerPacientePorUsuario(usuarioId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> obtenerTodos() {
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<?> obtenerPorClinica(@PathVariable Long clinicaId) {
        try {
            return ResponseEntity.ok(pacienteService.obtenerPacientesClinica(clinicaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody PacienteRequest datos) {
        try {
            return ResponseEntity.ok(pacienteService.actualizarPaciente(id, datos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
