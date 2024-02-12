package com.creditos.solicitudes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitudes_log_motivos")
public class SolicitudLogMotivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long idEstatusMotivo;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idSolicitudLog")
    private SolicitudLog solicitudLog;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String codigo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

}
