package com.Smillio.app.controllers;

import com.Smillio.app.dtos.CitaRequest;
import com.Smillio.app.dtos.CitaResponse;
import com.Smillio.app.services.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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

    // By Paciente.id
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponse>> obtenerCitasPaciente(@PathVariable Long pacienteId) {
        try {
            return ResponseEntity.ok(citaService.obtenerCitasPaciente(pacienteId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // By Usuarios.id (the logged-in user id from the frontend)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CitaResponse>> obtenerCitasPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(citaService.obtenerCitasPorUsuario(usuarioId));
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

    @GetMapping("/odontologo/{odontologoId}")
    public ResponseEntity<List<CitaResponse>> obtenerCitasOdontologo(@PathVariable Long odontologoId) {
        try {
            return ResponseEntity.ok(citaService.obtenerCitasOdontologo(odontologoId));
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

    // Body: { "fecha": "2025-06-15", "hora": "10:30" }
    @PutMapping("/{id}/reagendar")
    public ResponseEntity<CitaResponse> reagendar(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            LocalDate fecha = LocalDate.parse(body.get("fecha"));
            LocalTime hora = LocalTime.parse(body.get("hora"));
            return ResponseEntity.ok(citaService.reagendar(id, fecha, hora));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Body: { "nuevoOdontologoId": 5 }
    @PutMapping("/{id}/delegar")
    public ResponseEntity<CitaResponse> delegar(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        try {
            Long nuevoOdontologoId = body.get("nuevoOdontologoId");
            return ResponseEntity.ok(citaService.delegar(id, nuevoOdontologoId));
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
