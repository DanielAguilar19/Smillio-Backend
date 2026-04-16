package com.Smillio.app.controllers;

import com.Smillio.app.dtos.HistorialClinicoRequest;
import com.Smillio.app.dtos.HistorialClinicoResponse;
import com.Smillio.app.services.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin("*")
public class HistorialClinicoController {

    @Autowired
    private HistorialClinicoService historialClinicoService;

    @PostMapping
    public ResponseEntity<HistorialClinicoResponse> crearRegistro(@RequestBody HistorialClinicoRequest request) {
        try {
            return ResponseEntity.ok(historialClinicoService.crearRegistro(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialClinicoResponse>> obtenerHistorialPaciente(@PathVariable Long pacienteId) {
        try {
            return ResponseEntity.ok(historialClinicoService.obtenerHistorialPaciente(pacienteId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialClinicoResponse> obtenerRegistro(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(historialClinicoService.obtenerRegistro(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialClinicoResponse> actualizarRegistro(@PathVariable Long id, @RequestBody HistorialClinicoRequest request) {
        try {
            return ResponseEntity.ok(historialClinicoService.actualizarRegistro(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
