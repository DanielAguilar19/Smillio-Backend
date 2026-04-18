package com.Smillio.app.services;

import com.Smillio.app.dtos.OdontologoRequest;
import com.Smillio.app.entities.DocumentoOdontologo;
import com.Smillio.app.entities.Odontologo;
import com.Smillio.app.entities.Usuarios;
import com.Smillio.app.repositories.OdontologoRepository;
import com.Smillio.app.repositories.usuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private usuariosRepo usuariosRepository;

    private final String UPLOAD_DIR = "uploads/documentos/odontologos/";

    // Crear nuevo odontólogo usando DTO (paso 2 del registro)
    public Odontologo registrarOdontologo(OdontologoRequest request) {
        if (request.getUsuarioId() == null) {
            throw new RuntimeException("usuarioId es requerido para registrar un odontólogo");
        }

        Usuarios usuario = usuariosRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + request.getUsuarioId()));

        // Validar que no exista otro odontólogo con ese usuario
        if (odontologoRepository.findByUsuarioId(request.getUsuarioId()).isPresent()) {
            throw new RuntimeException("Este usuario ya está registrado como odontólogo");
        }

        // Validar que la cédula sea única
        if (request.getCedula() != null &&
                odontologoRepository.findByCedula(request.getCedula()).isPresent()) {
            throw new RuntimeException("La cédula ya está registrada");
        }

        Odontologo odontologo = new Odontologo();
        odontologo.setUsuario(usuario);
        odontologo.setNombre(request.getNombre() != null ? request.getNombre() : "");
        odontologo.setApellido(request.getApellido());
        odontologo.setCedula(request.getCedula() != null ? request.getCedula() : "");
        odontologo.setEspecialidades(request.getEspecialidades());
        odontologo.setDescripcion(request.getDescripcion());
        odontologo.setTelefono(request.getTelefono());
        odontologo.setUbicacion(request.getUbicacion());
        odontologo.setEstado(request.getEstado() != null ? request.getEstado() : "DESEMPLEADO");
        odontologo.setEstaActivo(true);

        return odontologoRepository.save(odontologo);
    }

    // Obtener odontólogo por ID
    public Odontologo obtenerOdontologo(Long id) {
        return odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado"));
    }

    // Obtener odontólogo por usuario ID
    public Odontologo obtenerOdontologoPorUsuario(Long usuarioId) {
        return odontologoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado para este usuario"));
    }

    // Obtener todos los odontólogos activos
    public List<Odontologo> obtenerOdontologosActivos() {
        return odontologoRepository.findByEstaActivoTrue();
    }

    // Obtener odontólogos disponibles (buscando empleo o desempleados)
    public List<Odontologo> obtenerOdontologosDisponibles() {
        return odontologoRepository.findAvailableOdontologos();
    }

    // Buscar por ubicación
    public List<Odontologo> buscarPorUbicacion(String ubicacion) {
        return odontologoRepository.findByUbicacion(ubicacion);
    }

    // Buscar por especialidad
    public List<Odontologo> buscarPorEspecialidad(String especialidad) {
        return odontologoRepository.findByEspecialidad(especialidad);
    }

    // Obtener odontólogos de una clínica
    public List<Odontologo> obtenerOdontologosClínica(Long clinicaId) {
        return odontologoRepository.findByClinicaActualId(clinicaId);
    }

    // Actualizar perfil de odontólogo
    public Odontologo actualizarPerfil(Long id, Odontologo datosActualizados) {
        Odontologo odontologo = obtenerOdontologo(id);

        if (datosActualizados.getNombre() != null) {
            odontologo.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getApellido() != null) {
            odontologo.setApellido(datosActualizados.getApellido());
        }
        if (datosActualizados.getEspecialidades() != null) {
            odontologo.setEspecialidades(datosActualizados.getEspecialidades());
        }
        if (datosActualizados.getDescripcion() != null) {
            odontologo.setDescripcion(datosActualizados.getDescripcion());
        }
        if (datosActualizados.getTelefono() != null) {
            odontologo.setTelefono(datosActualizados.getTelefono());
        }
        if (datosActualizados.getUbicacion() != null) {
            odontologo.setUbicacion(datosActualizados.getUbicacion());
        }
        if (datosActualizados.getEstado() != null) {
            odontologo.setEstado(datosActualizados.getEstado());
        }

        return odontologoRepository.save(odontologo);
    }

    // Actualizar estado de empleo
    public Odontologo actualizarEstado(Long id, String nuevoEstado) {
        Odontologo odontologo = obtenerOdontologo(id);
        // Validar que el estado sea válido: DESEMPLEADO, BUSCA_EMPLEO, EMPLEADO
        if (!nuevoEstado.matches("DESEMPLEADO|BUSCA_EMPLEO|EMPLEADO")) {
            throw new RuntimeException("Estado inválido. Debe ser: DESEMPLEADO, BUSCA_EMPLEO o EMPLEADO");
        }
        odontologo.setEstado(nuevoEstado);
        return odontologoRepository.save(odontologo);
    }

    // Desactivar odontólogo
    public void desactivarOdontologo(Long id) {
        Odontologo odontologo = obtenerOdontologo(id);
        odontologo.setEstaActivo(false);
        odontologoRepository.save(odontologo);
    }

    // Actualizar rating
    public Odontologo actualizarRating(Long id, Double nuevoRating) {
        Odontologo odontologo = obtenerOdontologo(id);
        odontologo.setRating(nuevoRating);
        return odontologoRepository.save(odontologo);
    }

    // Actualizar cantidad de reseñas
    public Odontologo incrementarResenas(Long id) {
        Odontologo odontologo = obtenerOdontologo(id);
        Integer total = odontologo.getTotalResenas() != null ? odontologo.getTotalResenas() : 0;
        odontologo.setTotalResenas(total + 1);
        return odontologoRepository.save(odontologo);
    }

    // Subir documento
    public DocumentoOdontologo subirDocumento(Long odontologoId, MultipartFile file, DocumentoOdontologo.TipoDocumento tipo) throws IOException {
        Odontologo odontologo = obtenerOdontologo(odontologoId);

        // Validar tipo de archivo (solo PDF e imágenes permitidas)
        String contentType = file.getContentType();
        if (!contentType.equals("application/pdf") &&
            !contentType.equals("image/jpeg") &&
            !contentType.equals("image/png") &&
            !contentType.equals("image/jpg")) {
            throw new RuntimeException("Solo se permiten archivos PDF e imágenes (JPG, PNG)");
        }

        // Validar tamaño (máximo 5MB)
        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new RuntimeException("El archivo excede el tamaño máximo de 5MB");
        }

        // Crear directorio si no existe
        Path uploadPath = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadPath);

        // Generar nombre único para el archivo
        String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(nombreArchivo);

        // Guardar archivo
        Files.write(filePath, file.getBytes());

        // Crear registro de documento
        DocumentoOdontologo documento = new DocumentoOdontologo();
        documento.setOdontologo(odontologo);
        documento.setTipo(tipo);
        documento.setNombreArchivo(file.getOriginalFilename());
        documento.setRutaArchivo(filePath.toString());
        documento.setTamaño(file.getSize());
        documento.setVerificado(false);

        odontologo.getDocumentos().add(documento);
        odontologoRepository.save(odontologo);

        return documento;
    }

    // Obtener documentos de un odontólogo
    public List<DocumentoOdontologo> obtenerDocumentos(Long odontologoId) {
        Odontologo odontologo = obtenerOdontologo(odontologoId);
        return odontologo.getDocumentos();
    }

    // Verificar documento (por clínica)
    public DocumentoOdontologo verificarDocumento(Long odontologoId, Long documentoId, Boolean verificado, String notas) {
        Odontologo odontologo = obtenerOdontologo(odontologoId);

        DocumentoOdontologo documento = odontologo.getDocumentos().stream()
                .filter(d -> d.getId().equals(documentoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        documento.setVerificado(verificado);
        documento.setNotas(notas);
        return odontologoRepository.save(odontologo).getDocumentos().stream()
                .filter(d -> d.getId().equals(documentoId))
                .findFirst()
                .get();
    }

    // Eliminar documento
    public void eliminarDocumento(Long odontologoId, Long documentoId) {
        Odontologo odontologo = obtenerOdontologo(odontologoId);

        DocumentoOdontologo documento = odontologo.getDocumentos().stream()
                .filter(d -> d.getId().equals(documentoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        // Intentar eliminar archivo del disco
        try {
            Files.deleteIfExists(Paths.get(documento.getRutaArchivo()));
        } catch (IOException e) {
            System.err.println("Error al eliminar archivo: " + e.getMessage());
        }

        // Eliminar registro de la base de datos (cascada automática)
        odontologo.getDocumentos().remove(documento);
        odontologoRepository.save(odontologo);
    }
}
