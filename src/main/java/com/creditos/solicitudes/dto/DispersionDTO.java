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
public class DispersionDTO {
    @NotBlank(message = "El id de solicitud no puede estar vacío")
    private String idSolicitud;
    @NotNull(message = "El id de crédito no puede estar vacío")
    private Long idCredito;
    @NotNull(message = "El capital dispersado no puede estar vacío")
    @PositiveOrZero(message = "El capital dispersado no puede ser negativo")
    private Double capitalDispersado;
    @NotNull(message = "El monto no puede estar vacío")
    @PositiveOrZero(message = "El monto no puede ser negativo")
    private Double monto;
    @NotNull(message = "La tasa no puede estar vacía")
    @PositiveOrZero(message = "La tasa no puede ser negativa")
    private Double tasa;
    @NotNull(message = "El plazo no puede estar vacío")
    @PositiveOrZero(message = "El plazo no puede ser negativo")
    private Integer plazo;
    @NotBlank(message = "La frecuencia no puede estar vacía")
    private String frecuencia;
}
