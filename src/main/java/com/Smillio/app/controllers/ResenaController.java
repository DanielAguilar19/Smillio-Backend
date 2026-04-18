package com.Smillio.app.controllers;

import com.Smillio.app.dtos.ResenaRequest;
import com.Smillio.app.dtos.ResenaResponse;
import com.Smillio.app.dtos.ResponderResenaRequest;
import com.Smillio.app.services.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resenas")
@CrossOrigin("*")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @PostMapping
    public ResponseEntity<ResenaResponse> crearResena(@RequestBody ResenaRequest request) {
        try {
            return ResponseEntity.ok(resenaService.crearResena(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<ResenaResponse>> obtenerResenasClinica(@PathVariable Long clinicaId) {
        try {
            return ResponseEntity.ok(resenaService.obtenerResenasClinica(clinicaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/clinica/{clinicaId}/pendientes")
    public ResponseEntity<List<ResenaResponse>> obtenerResenasPendientes(@PathVariable Long clinicaId) {
        try {
            return ResponseEntity.ok(resenaService.obtenerResenasPendientes(clinicaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}/responder")
    public ResponseEntity<ResenaResponse> responderResena(@PathVariable Long id, @RequestBody ResponderResenaRequest request) {
        try {
            return ResponseEntity.ok(resenaService.responderResena(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/clinica/{clinicaId}/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@PathVariable Long clinicaId) {
        try {
            return ResponseEntity.ok(resenaService.obtenerEstadisticas(clinicaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
