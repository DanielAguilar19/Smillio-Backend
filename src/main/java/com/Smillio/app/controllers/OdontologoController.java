package com.Smillio.app.controllers;

import com.Smillio.app.dtos.OdontologoRequest;
import com.Smillio.app.entities.DocumentoOdontologo;
import com.Smillio.app.entities.Odontologo;
import com.Smillio.app.services.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/odontologos")
@CrossOrigin(origins = "*")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    // Registrar nuevo odontólogo (paso 2: ya existe el usuario, se vincula el perfil)
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody OdontologoRequest request) {
        try {
            Odontologo nuevoOdontologo = odontologoService.registrarOdontologo(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoOdontologo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener odontólogo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            Odontologo odontologo = odontologoService.obtenerOdontologo(id);
            return ResponseEntity.ok(odontologo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener odontólogo por usuario ID
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long usuarioId) {
        try {
            Odontologo odontologo = odontologoService.obtenerOdontologoPorUsuario(usuarioId);
            return ResponseEntity.ok(odontologo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener todos los odontólogos activos
    @GetMapping
    public ResponseEntity<List<Odontologo>> obtenerTodos() {
        List<Odontologo> odontologos = odontologoService.obtenerOdontologosActivos();
        return ResponseEntity.ok(odontologos);
    }

    // Obtener odontólogos disponibles (para contratación)
    @GetMapping("/disponibles")
    public ResponseEntity<List<Odontologo>> obtenerDisponibles() {
        List<Odontologo> odontologos = odontologoService.obtenerOdontologosDisponibles();
        return ResponseEntity.ok(odontologos);
    }

    // Buscar por ubicación
    @GetMapping("/buscar/ubicacion/{ubicacion}")
    public ResponseEntity<List<Odontologo>> buscarPorUbicacion(@PathVariable String ubicacion) {
        List<Odontologo> odontologos = odontologoService.buscarPorUbicacion(ubicacion);
        return ResponseEntity.ok(odontologos);
    }

    // Buscar por especialidad
    @GetMapping("/buscar/especialidad/{especialidad}")
    public ResponseEntity<List<Odontologo>> buscarPorEspecialidad(@PathVariable String especialidad) {
        List<Odontologo> odontologos = odontologoService.buscarPorEspecialidad(especialidad);
        return ResponseEntity.ok(odontologos);
    }

    // Obtener odontólogos de una clínica
    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<Odontologo>> obtenerPorClinica(@PathVariable Long clinicaId) {
        List<Odontologo> odontologos = odontologoService.obtenerOdontologosClínica(clinicaId);
        return ResponseEntity.ok(odontologos);
    }

    // Actualizar perfil
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Odontologo datosActualizados) {
        try {
            Odontologo actualizado = odontologoService.actualizarPerfil(id, datosActualizados);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Actualizar estado de empleo
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            Odontologo actualizado = odontologoService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Desactivar odontólogo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        try {
            odontologoService.desactivarOdontologo(id);
            return ResponseEntity.ok(Map.of("mensaje", "Odontólogo desactivado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Subir documento
    @PostMapping("/{id}/documentos")
    public ResponseEntity<?> subirDocumento(
            @PathVariable Long id,
            @RequestParam MultipartFile archivo,
            @RequestParam DocumentoOdontologo.TipoDocumento tipo) {
        try {
            DocumentoOdontologo documento = odontologoService.subirDocumento(id, archivo, tipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(documento);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar el archivo"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener documentos de un odontólogo
    @GetMapping("/{id}/documentos")
    public ResponseEntity<?> obtenerDocumentos(@PathVariable Long id) {
        try {
            List<DocumentoOdontologo> documentos = odontologoService.obtenerDocumentos(id);
            return ResponseEntity.ok(documentos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Verificar documento (por clínica)
    @PutMapping("/{id}/documentos/{documentoId}/verificar")
    public ResponseEntity<?> verificarDocumento(
            @PathVariable Long id,
            @PathVariable Long documentoId,
            @RequestBody Map<String, Object> body) {
        try {
            Boolean verificado = (Boolean) body.get("verificado");
            String notas = (String) body.get("notas");
            DocumentoOdontologo documento = odontologoService.verificarDocumento(id, documentoId, verificado, notas);
            return ResponseEntity.ok(documento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar documento
    @DeleteMapping("/{id}/documentos/{documentoId}")
    public ResponseEntity<?> eliminarDocumento(@PathVariable Long id, @PathVariable Long documentoId) {
        try {
            odontologoService.eliminarDocumento(id, documentoId);
            return ResponseEntity.ok(Map.of("mensaje", "Documento eliminado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
