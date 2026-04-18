package com.Smillio.app.controllers;

import com.Smillio.app.dtos.EstadisticasFacturasResponse;
import com.Smillio.app.dtos.FacturaRequest;
import com.Smillio.app.dtos.FacturaResponse;
import com.Smillio.app.dtos.PagarFacturaRequest;
import com.Smillio.app.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin("*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<FacturaResponse> crearFactura(@RequestBody FacturaRequest request) {
        try {
            return ResponseEntity.ok(facturaService.crearFactura(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> obtenerFactura(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facturaService.obtenerFactura(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<FacturaResponse>> obtenerFacturasClinica(@PathVariable Long clinicaId) {
        try {
            return ResponseEntity.ok(facturaService.obtenerFacturasClinica(clinicaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<FacturaResponse>> obtenerFacturasPaciente(@PathVariable Long pacienteId) {
        try {
            return ResponseEntity.ok(facturaService.obtenerFacturasPaciente(pacienteId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/clinica/{clinicaId}/estado/{estado}")
    public ResponseEntity<List<FacturaResponse>> obtenerFacturasPorEstado(
            @PathVariable Long clinicaId, @PathVariable String estado) {
        try {
            return ResponseEntity.ok(facturaService.obtenerFacturasPorEstado(clinicaId, estado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<FacturaResponse> pagarFactura(
            @PathVariable Long id, @RequestBody PagarFacturaRequest request) {
        try {
            return ResponseEntity.ok(facturaService.pagarFactura(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/clinica/{clinicaId}/estadisticas")
    public ResponseEntity<EstadisticasFacturasResponse> obtenerEstadisticas(@PathVariable Long clinicaId) {
        try {
            return ResponseEntity.ok(facturaService.obtenerEstadisticas(clinicaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
