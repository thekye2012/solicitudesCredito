package com.creditos.solicitudes.repository;

import com.creditos.solicitudes.entities.Solicitud;
import com.creditos.solicitudes.entities.SolicitudCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario

    SolicitudCredito findBySolicitud(Solicitud solicitud);
}