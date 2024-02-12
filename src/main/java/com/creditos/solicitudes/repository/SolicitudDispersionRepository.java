package com.creditos.solicitudes.repository;

import com.creditos.solicitudes.entities.SolicitudDispersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudDispersionRepository extends JpaRepository<SolicitudDispersion, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
