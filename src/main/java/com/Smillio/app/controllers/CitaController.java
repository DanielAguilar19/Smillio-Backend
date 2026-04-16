package com.Smillio.app.controllers;

import com.Smillio.app.dtos.CitaRequest;
import com.Smillio.app.dtos.CitaResponse;
import com.Smillio.app.services.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin("*")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaResponse> crearCita(@RequestBody CitaRequest request) {
        try {
            return ResponseEntity.ok(citaService.crearCita(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerCita(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(citaService.obtenerCita(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponse>> obtenerCitasPaciente(@PathVariable Long pacienteId) {
        try {
            return ResponseEntity.ok(citaService.obtenerCitasPaciente(pacienteId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<CitaResponse>> obtenerCitasClinica(@PathVariable Long clinicaId) {
        try {
            return ResponseEntity.ok(citaService.obtenerCitasClinica(clinicaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CitaResponse> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        try {
            return ResponseEntity.ok(citaService.actualizarEstado(id, nuevoEstado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarCita(@PathVariable Long id) {
        try {
            citaService.cancelarCita(id);
            return ResponseEntity.ok("Cita cancelada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
