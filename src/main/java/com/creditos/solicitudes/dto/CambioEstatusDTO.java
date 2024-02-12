package com.creditos.solicitudes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Valid
public class CambioEstatusDTO {
    @NotBlank(message = "El id de solicitud no puede estar vacío")
    private String idSolicitud;

    @NotBlank(message = "El estatus no puede estar vacío")
    private String idEstatus;

    @Valid
    private List<CambioEstatusMotivoDTO> motivo;

    @NotBlank(message = "La fecha de cambio no puede estar vacía")
    private String fechaCambio;
}
