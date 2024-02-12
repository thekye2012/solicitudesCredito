package com.creditos.solicitudes.repository;

import com.creditos.solicitudes.entities.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}