package com.creditos.solicitudes.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "solicitudes")
@JsonIgnoreProperties({"solicitudCredito"})
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String idSolicitud;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private Integer monto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String producto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String tipoSolicitudStr;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String idTipoSolicitud;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private Integer tasa;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private Integer plazo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String frecuencia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fechaSolicitud;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String idEstatus;

    @OneToOne
    private SolicitudCredito solicitudCredito;


}
