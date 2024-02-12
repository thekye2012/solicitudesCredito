package com.creditos.solicitudes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class CambioEstatusMotivoDTO {
    @NotBlank(message = "El código del motivo no puede estar vacío")
    private String codigo;
    @NotBlank(message = "La descripción del motivo no puede estar vacía")
    private String descripcion;
}
