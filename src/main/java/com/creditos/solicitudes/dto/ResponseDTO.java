package com.creditos.solicitudes.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Schema(description = "Respuesta de la API")
public class ResponseDTO {
    @ApiModelProperty(value = "Indica si la operación fue exitosa")
    private Boolean success;
    @ApiModelProperty(value = "Código de estado de la respuesta")
    private int status;
    @ApiModelProperty(value = "Datos de la respuesta")
    private Object data;
    @ApiModelProperty(value = "Marca de tiempo de la respuesta")
    private String timestamp = LocalDateTime.now().toString();

    public ResponseDTO(Boolean success, int status, Object data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

}
