package com.creditos.solicitudes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class SolicitudDTO {
    @NotBlank(message = "El id de la solicitud no puede estar vacío")
    private String idSolicitud;

    @NotNull(message = "El monto no puede estar vacío")
    @PositiveOrZero(message = "El monto no puede ser negativo")
    private Integer monto;

    @NotBlank(message = "El producto no puede estar vacío")
    private String producto;

    @NotBlank(message = "El tipo de solicitud no puede estar vacío")
    private String tipoSolicitudStr;

    @NotBlank(message = "El id del tipo de solicitud no puede estar vacío")
    private String idTipoSolicitud;

    @NotNull(message = "La tasa no puede estar vacía")
    @PositiveOrZero(message = "La tasa no puede ser negativa")
    private Integer tasa;

    @NotNull(message = "El plazo no puede estar vacío")
    @PositiveOrZero(message = "El plazo no puede ser negativo")
    private Integer plazo;

    @NotBlank(message = "La frecuencia no puede estar vacía")
    private String frecuencia;

    @NotBlank(message = "La fecha de solicitud no puede estar vacía")
    private String fechaSolicitud;

    private String idEstatus;

}
