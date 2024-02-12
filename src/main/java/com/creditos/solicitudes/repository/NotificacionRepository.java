package com.creditos.solicitudes.repository;

import com.creditos.solicitudes.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}