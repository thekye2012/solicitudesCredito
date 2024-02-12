package com.creditos.solicitudes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitudes_log")
public class SolicitudLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long idSolicitudLog;

    @ManyToOne
    private SolicitudCredito solicitudCredito;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String idEstatus;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fechaCambio;

    @OneToMany(mappedBy = "solicitudLog")
    private List<SolicitudLogMotivo> solicitudLogMotivos;
}
