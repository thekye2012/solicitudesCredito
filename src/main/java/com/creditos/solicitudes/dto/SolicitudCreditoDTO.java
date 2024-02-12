package com.creditos.solicitudes.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Solicitud de crédito")
public class SolicitudCreditoDTO {
    @ApiModelProperty(value = "El promotor de la solicitud", required = true, example = "Juan Pérez")
    @NotBlank(message = "El promotor no puede estar vacío")
    private String promotor;

    @NotBlank(message = "La empresa no puede estar vacía")
    private String empresa;

    @NotNull(message = "El cliente no puede estar vacío")
    @Valid
    private ClienteDTO cliente;

    @NotNull(message = "La solicitud no puede estar vacía")
    @Valid
    private SolicitudDTO solicitud;


}
