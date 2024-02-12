package com.creditos.solicitudes.repository;

import com.creditos.solicitudes.entities.SolicitudLogMotivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudLogMotivoRepository extends JpaRepository<SolicitudLogMotivo, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}