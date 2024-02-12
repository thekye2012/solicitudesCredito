package com.creditos.solicitudes.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "creditos")
@JsonIgnoreProperties({"cliente"})
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long idCredito;

    @OneToOne
    private SolicitudCredito solicitudCredito;

    @ManyToOne
    private Cliente cliente;

    @Column(nullable = false)
    private Double capitalDispersado;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private Integer plazo;

    @Column(nullable = false)
    private Double tasa;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String frecuencia;

    @Column(nullable = false)
    private LocalDate fechaCredito;
}
