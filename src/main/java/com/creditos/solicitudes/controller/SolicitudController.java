package com.creditos.solicitudes.controller;

import com.creditos.solicitudes.dto.CambioEstatusDTO;
import com.creditos.solicitudes.dto.DispersionDTO;
import com.creditos.solicitudes.dto.ResponseDTO;
import com.creditos.solicitudes.dto.SolicitudCreditoDTO;
import com.creditos.solicitudes.entities.Credito;
import com.creditos.solicitudes.entities.Solicitud;
import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.service.SolicitudCreditoService;
import com.creditos.solicitudes.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
@Validated
public class SolicitudController {


    private final SolicitudCreditoService solicitudCreditoService;
    private final SolicitudService solicitudService;

    @Operation(summary = "Alta de solicitud de crédito", description = "Registra una nueva solicitud de crédito", tags = {"solicitudes"})

    @PostMapping("/alta")
    public ResponseEntity<ResponseDTO> altaSolicitudCredito(@Valid @RequestBody SolicitudCreditoDTO solicitudCreditoDTO) {
        SolicitudCredito solicitudCredito = solicitudCreditoService.altaSolicitudCredito(solicitudCreditoDTO);
        ResponseDTO response = new ResponseDTO(true, 200, solicitudCredito);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cambio de estatus de solicitud de crédito", description = "Cambia el estatus de una solicitud de crédito", tags = {"solicitudes"})
    @PatchMapping("/cambioEstatus")
    public ResponseEntity<ResponseDTO> cambioEstatusSolicitudCredito(@Valid @RequestBody CambioEstatusDTO cambioEstatusDTO) {
        Solicitud solicitud = solicitudService.cambioEstatus(cambioEstatusDTO);
        ResponseDTO response = new ResponseDTO(true, 200, solicitud);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Dispersion de solicitud de crédito", description = "Dispersion de una solicitud de crédito", tags = {"solicitudes"})
    @PutMapping("/dispersar")
    public ResponseEntity<ResponseDTO> dispersarSolicitudCredito(@Valid @RequestBody DispersionDTO dispersionDTO) {
        Credito solicitud = solicitudService.dispersarSolicitud(dispersionDTO);
        ResponseDTO response = new ResponseDTO(true, 200, solicitud);
        return ResponseEntity.ok(response);
    }

}
