package com.Smillio.app.controllers;

import com.Smillio.app.dtos.ClinicaRequest;
import com.Smillio.app.dtos.ClinicaResponse;
import com.Smillio.app.dtos.EspecialidadRequest;
import com.Smillio.app.entities.Especialidad;
import com.Smillio.app.entities.Factura;
import com.Smillio.app.entities.Resena;
import com.Smillio.app.entities.Usuarios;
import com.Smillio.app.repositories.FacturaRepository;
import com.Smillio.app.repositories.ResenaRepository;
import com.Smillio.app.repositories.usuariosRepo;
import com.Smillio.app.services.ClinicaService;
import com.Smillio.app.services.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired private usuariosRepo usuariosRepository;
    @Autowired private EspecialidadService especialidadService;
    @Autowired private ClinicaService clinicaService;
    @Autowired private ResenaRepository resenaRepository;
    @Autowired private FacturaRepository facturaRepository;

    // ── User management ───────────────────────────────────────────────

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        return ResponseEntity.ok(usuariosRepository.findAll());
    }

    @PutMapping("/usuarios/{id}/activar")
    public ResponseEntity<?> activarUsuario(@PathVariable Long id) {
        try {
            Usuarios u = usuariosRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            u.setEstaActivo(true);
            return ResponseEntity.ok(usuariosRepository.save(u));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/usuarios/{id}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@PathVariable Long id) {
        try {
            Usuarios u = usuariosRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            u.setEstaActivo(false);
            return ResponseEntity.ok(usuariosRepository.save(u));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            usuariosRepository.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── Specialty CRUD ────────────────────────────────────────────────

    @GetMapping("/especialidades")
    public ResponseEntity<List<Especialidad>> listarEspecialidades() {
        return ResponseEntity.ok(especialidadService.listarTodas());
    }

    @PostMapping("/especialidades")
    public ResponseEntity<?> crearEspecialidad(@RequestBody EspecialidadRequest request) {
        try {
            return ResponseEntity.ok(especialidadService.crear(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/especialidades/{id}")
    public ResponseEntity<?> actualizarEspecialidad(@PathVariable Long id, @RequestBody EspecialidadRequest request) {
        try {
            return ResponseEntity.ok(especialidadService.actualizar(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/especialidades/{id}")
    public ResponseEntity<?> eliminarEspecialidad(@PathVariable Long id) {
        try {
            especialidadService.eliminar(id);
            return ResponseEntity.ok("Especialidad desactivada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── Create clinic account ─────────────────────────────────────────

    @PostMapping("/clinicas")
    public ResponseEntity<?> crearClinica(@RequestBody ClinicaRequest request) {
        try {
            ClinicaResponse response = clinicaService.crearClinica(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── Review monitoring ─────────────────────────────────────────────

    @GetMapping("/resenas")
    public ResponseEntity<List<Resena>> listarTodasResenas() {
        return ResponseEntity.ok(resenaRepository.findAll());
    }

    @GetMapping("/resenas/pendientes")
    public ResponseEntity<List<Resena>> listarResenasPendientes() {
        // Reseñas sin respuesta de ninguna clínica
        List<Resena> pendientes = resenaRepository.findAll().stream()
                .filter(r -> Boolean.FALSE.equals(r.getRespondida()) || r.getRespondida() == null)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pendientes);
    }

    // ── Monthly revenue stats ─────────────────────────────────────────

    /**
     * Returns monthly revenue aggregated from paid invoices.
     * Query param: year (default = current year)
     * Response: [ { "mes": 1, "total": 15000.0 }, ... ]
     */
    @GetMapping("/ingresos/mensuales")
    public ResponseEntity<List<Map<String, Object>>> ingresosMensuales(
            @RequestParam(defaultValue = "0") int year) {
        int targetYear = (year == 0) ? LocalDate.now().getYear() : year;

        List<Factura> facturas = facturaRepository.findAll().stream()
                .filter(f -> "PAGADA".equals(f.getEstado())
                        && f.getFecha() != null
                        && f.getFecha().getYear() == targetYear)
                .collect(Collectors.toList());

        Map<Integer, Double> totalesPorMes = new HashMap<>();
        for (int m = 1; m <= 12; m++) totalesPorMes.put(m, 0.0);
        for (Factura f : facturas) {
            int mes = f.getFecha().getMonthValue();
            totalesPorMes.merge(mes, f.getMonto() != null ? f.getMonto() : 0.0, Double::sum);
        }

        List<Map<String, Object>> result = totalesPorMes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> {
                    Map<String, Object> row = new HashMap<>();
                    row.put("mes", e.getKey());
                    row.put("total", e.getValue());
                    return row;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    /**
     * Revenue per clinic for a given year/month.
     * Response: [ { "clinicaId": 1, "clinicaNombre": "...", "total": 5000.0 }, ... ]
     */
    @GetMapping("/ingresos/clinicas")
    public ResponseEntity<List<Map<String, Object>>> ingresosPorClinica(
            @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int mes) {
        int targetYear = (year == 0) ? LocalDate.now().getYear() : year;

        List<Factura> facturas = facturaRepository.findAll().stream()
                .filter(f -> "PAGADA".equals(f.getEstado())
                        && f.getFecha() != null
                        && f.getFecha().getYear() == targetYear
                        && (mes == 0 || f.getFecha().getMonthValue() == mes))
                .collect(Collectors.toList());

        Map<Long, double[]> porClinica = new HashMap<>();
        Map<Long, String> nombres = new HashMap<>();
        for (Factura f : facturas) {
            if (f.getClinica() == null) continue;
            Long cid = f.getClinica().getId();
            nombres.put(cid, f.getClinica().getNombre());
            porClinica.computeIfAbsent(cid, k -> new double[]{0.0})[0] +=
                    f.getMonto() != null ? f.getMonto() : 0.0;
        }

        List<Map<String, Object>> result = porClinica.entrySet().stream()
                .map(e -> {
                    Map<String, Object> row = new HashMap<>();
                    row.put("clinicaId", e.getKey());
                    row.put("clinicaNombre", nombres.get(e.getKey()));
                    row.put("total", e.getValue()[0]);
                    return row;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
