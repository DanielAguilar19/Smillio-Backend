package com.Smillio.app.controllers;

import com.Smillio.app.dtos.ClinicaRequest;
import com.Smillio.app.dtos.ClinicaResponse;
import com.Smillio.app.dtos.HorarioDiaRequest;
import com.Smillio.app.dtos.ServicioRequest;
import com.Smillio.app.dtos.ServicioResponse;
import com.Smillio.app.services.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinicas")
@CrossOrigin("*")
public class ClinicaController {

    @Autowired
    private ClinicaService clinicaService;

    @GetMapping
    public ResponseEntity<List<ClinicaResponse>> listarTodasClinicas() {
        try {
            return ResponseEntity.ok(clinicaService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(clinicaService.getByUsuario(usuarioId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicaResponse> obtenerClinica(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clinicaService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicaResponse> actualizarClinica(@PathVariable Long id, @RequestBody ClinicaRequest request) {
        try {
            return ResponseEntity.ok(clinicaService.update(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}/servicios")
    public ResponseEntity<List<ServicioResponse>> obtenerServicios(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clinicaService.getServicios(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{id}/servicios")
    public ResponseEntity<ServicioResponse> agregarServicio(@PathVariable Long id, @RequestBody ServicioRequest request) {
        try {
            return ResponseEntity.ok(clinicaService.addServicio(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{clinicaId}/servicios/{servicioId}")
    public ResponseEntity<ServicioResponse> actualizarServicio(@PathVariable Long clinicaId, @PathVariable Long servicioId, @RequestBody ServicioRequest request) {
        try {
            return ResponseEntity.ok(clinicaService.updateServicio(clinicaId, servicioId, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{clinicaId}/servicios/{servicioId}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long clinicaId, @PathVariable Long servicioId) {
        try {
            clinicaService.deleteServicio(clinicaId, servicioId);
            return ResponseEntity.ok("Servicio eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/horario")
    public ResponseEntity<ClinicaResponse> actualizarHorario(@PathVariable Long id, @RequestBody List<HorarioDiaRequest> horario) {
        try {
            return ResponseEntity.ok(clinicaService.updateHorario(id, horario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
