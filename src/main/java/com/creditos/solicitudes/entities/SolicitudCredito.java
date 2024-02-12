package com.creditos.solicitudes.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "solicitudes_credito")
@JsonIgnoreProperties({"solicitudesLog", "solicitudDispersion"})
public class SolicitudCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long idSolicitudCredito;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String promotor;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String empresa;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idCliente")
    private Cliente cliente;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(referencedColumnName = "id")
    private Solicitud solicitud;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SolicitudLog> solicitudesLog;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private SolicitudDispersion solicitudDispersion;
}
