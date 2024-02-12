package com.creditos.solicitudes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitudes_dispersion")
public class SolicitudDispersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long idSolicitudDispersion;

    @OneToOne
    @JoinColumn(referencedColumnName = "idSolicitudCredito")
    private SolicitudCredito solicitudCredito;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Credito credito;

    @Column(nullable = false)
    private Double capitalDispersado;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Double tasa;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String frecuencia;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private Integer plazo;

    @Column(nullable = false)
    private LocalDate fechaDispersion;
}
