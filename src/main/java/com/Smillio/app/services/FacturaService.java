package com.Smillio.app.services;

import com.Smillio.app.dtos.EstadisticasFacturasResponse;
import com.Smillio.app.dtos.FacturaRequest;
import com.Smillio.app.dtos.FacturaResponse;
import com.Smillio.app.dtos.PagarFacturaRequest;
import com.Smillio.app.entities.Clinica;
import com.Smillio.app.entities.Factura;
import com.Smillio.app.entities.Paciente;
import com.Smillio.app.repositories.ClinicaRepository;
import com.Smillio.app.repositories.FacturaRepository;
import com.Smillio.app.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    public FacturaResponse crearFactura(FacturaRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Clinica clinica = clinicaRepository.findById(request.getClinicaId())
                .orElseThrow(() -> new RuntimeException("Clinica no encontrada"));

        Factura factura = new Factura();
        factura.setPaciente(paciente);
        factura.setClinica(clinica);
        factura.setFecha(request.getFecha() != null ? request.getFecha() : LocalDate.now());
        factura.setServicio(request.getServicio());
        factura.setMonto(request.getMonto());
        factura.setEstado(request.getEstado() != null ? request.getEstado() : "PENDIENTE");
        factura.setMetodoPago(request.getMetodoPago());

        Factura guardada = facturaRepository.save(factura);
        return toResponse(guardada);
    }

    public FacturaResponse obtenerFactura(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        return toResponse(factura);
    }

    public List<FacturaResponse> obtenerFacturasClinica(Long clinicaId) {
        return facturaRepository.findByClinicaId(clinicaId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<FacturaResponse> obtenerFacturasPaciente(Long pacienteId) {
        return facturaRepository.findByPacienteId(pacienteId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<FacturaResponse> obtenerFacturasPorEstado(Long clinicaId, String estado) {
        return facturaRepository.findByClinicaIdAndEstado(clinicaId, estado)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public FacturaResponse pagarFactura(Long id, PagarFacturaRequest request) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        factura.setEstado("PAGADA");
        factura.setMetodoPago(request.getMetodoPago());
        return toResponse(facturaRepository.save(factura));
    }

    public EstadisticasFacturasResponse obtenerEstadisticas(Long clinicaId) {
        List<Factura> facturas = facturaRepository.findByClinicaId(clinicaId);

        Double totalPagado = facturas.stream()
                .filter(f -> "PAGADA".equals(f.getEstado()))
                .mapToDouble(Factura::getMonto)
                .sum();

        Double totalPendiente = facturas.stream()
                .filter(f -> "PENDIENTE".equals(f.getEstado()))
                .mapToDouble(Factura::getMonto)
                .sum();

        Double totalVencido = facturas.stream()
                .filter(f -> "VENCIDA".equals(f.getEstado()))
                .mapToDouble(Factura::getMonto)
                .sum();

        Long cantidadPagadas = facturas.stream()
                .filter(f -> "PAGADA".equals(f.getEstado()))
                .count();

        Long cantidadPendientes = facturas.stream()
                .filter(f -> "PENDIENTE".equals(f.getEstado()))
                .count();

        Long cantidadVencidas = facturas.stream()
                .filter(f -> "VENCIDA".equals(f.getEstado()))
                .count();

        return new EstadisticasFacturasResponse(
                totalPagado, totalPendiente, totalVencido,
                cantidadPagadas, cantidadPendientes, cantidadVencidas
        );
    }

    private FacturaResponse toResponse(Factura factura) {
        FacturaResponse response = new FacturaResponse();
        response.setId(factura.getId());
        response.setPacienteId(factura.getPaciente().getId());
        response.setPacienteNombre(factura.getPaciente().getNombre());
        response.setClinicaId(factura.getClinica().getId());
        response.setFecha(factura.getFecha());
        response.setServicio(factura.getServicio());
        response.setMonto(factura.getMonto());
        response.setEstado(factura.getEstado());
        response.setMetodoPago(factura.getMetodoPago());
        return response;
    }
}
