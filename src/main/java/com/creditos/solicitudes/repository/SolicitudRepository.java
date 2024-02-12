package com.creditos.solicitudes.repository;

import com.creditos.solicitudes.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    Boolean existsByIdSolicitud(String idSolicitud);
    
    Solicitud findByIdSolicitud(String idSolicitud);
}