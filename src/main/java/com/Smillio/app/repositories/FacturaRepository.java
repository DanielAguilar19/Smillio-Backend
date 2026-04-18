package com.Smillio.app.repositories;

import com.Smillio.app.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByClinicaId(Long clinicaId);
    List<Factura> findByClinicaIdAndEstado(Long clinicaId, String estado);
    List<Factura> findByPacienteId(Long pacienteId);
}
