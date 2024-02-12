package com.creditos.solicitudes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Valid
public class ClienteDTO {
    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido paterno del cliente no puede estar vacío")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno del cliente no puede estar vacío")
    private String apellidoMaterno;
}
