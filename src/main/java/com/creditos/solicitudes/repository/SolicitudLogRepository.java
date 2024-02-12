package com.creditos.solicitudes.repository;

import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.entities.SolicitudLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudLogRepository extends JpaRepository<SolicitudLog, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    Boolean existsBySolicitudCreditoAndIdEstatus(SolicitudCredito solicitudCredito, String estatus);
}
