package com.creditos.solicitudes.controller;

import com.creditos.solicitudes.dto.*;
import com.creditos.solicitudes.entities.Cliente;
import com.creditos.solicitudes.entities.Credito;
import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.service.SolicitudCreditoService;
import com.creditos.solicitudes.service.SolicitudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitudControllerTest {
    @Mock
    private SolicitudCreditoService solicitudCreditoService;
    @Mock
    private SolicitudService solicitudService;

    @InjectMocks
    private SolicitudController solicitudController;
    private ClienteDTO clienteDTO;
    private SolicitudDTO solicitudDTO;
    private SolicitudCreditoDTO solicitudCreditoDTO;
    private ResponseDTO responseDTO;
    private SolicitudCredito solicitudCredito;
    private Cliente cliente;
    private CambioEstatusDTO cambioEstatusDTO;
    private CambioEstatusMotivoDTO cambioEstatusMotivoDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("PERLA TOMASA");
        clienteDTO.setApellidoPaterno("CABRERA");
        clienteDTO.setApellidoMaterno("ROMAN");

        solicitudDTO = new SolicitudDTO();
        solicitudDTO.setIdSolicitud("110102044798");
        solicitudDTO.setFechaSolicitud("20220727");
        solicitudDTO.setIdEstatus("INGRESADA");
        solicitudDTO.setMonto(167000);
        solicitudDTO.setPlazo(0);
        solicitudDTO.setTasa(39);
        solicitudDTO.setFrecuencia("Semanal/Mensual/Catorcenal");
        solicitudDTO.setProducto("IMSS");
        solicitudDTO.setTipoSolicitudStr("CREDITO NUEVO");
        solicitudDTO.setIdTipoSolicitud("394");

        solicitudCreditoDTO = new SolicitudCreditoDTO();
        solicitudCreditoDTO.setCliente(clienteDTO);
        solicitudCreditoDTO.setSolicitud(solicitudDTO);
        solicitudCreditoDTO.setEmpresa("XXXXX");
        solicitudCreditoDTO.setPromotor("SLP34/CURP");

        responseDTO = new ResponseDTO(true, 200, solicitudCreditoDTO);

        cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setNombre("PERLA TOMASA");
        cliente.setApellidoPaterno("CABRERA");
        cliente.setApellidoMaterno("ROMAN");

        solicitudCredito = new SolicitudCredito();
        solicitudCredito.setIdSolicitudCredito(1L);
        solicitudCredito.setPromotor("SLP34/CURP");
        solicitudCredito.setEmpresa("XXXXX");
        solicitudCredito.setCliente(cliente);

        cambioEstatusMotivoDTO = new CambioEstatusMotivoDTO();
        cambioEstatusMotivoDTO.setCodigo("123");
        cambioEstatusMotivoDTO.setDescripcion("Documentos incompletos");


        cambioEstatusDTO = new CambioEstatusDTO();
        cambioEstatusDTO.setIdEstatus("APROB");
        cambioEstatusDTO.setIdSolicitud("110102044798");
        cambioEstatusDTO.setFechaCambio("20220727");
        cambioEstatusDTO.setMotivo(List.of(cambioEstatusMotivoDTO));


    }

    @Test
    void altaSolicitudCredito() {
        when(solicitudCreditoService.altaSolicitudCredito(any(SolicitudCreditoDTO.class))).thenReturn(solicitudCredito);

        ResponseEntity<ResponseDTO> response = solicitudController.altaSolicitudCredito(solicitudCreditoDTO);

        verify(solicitudCreditoService).altaSolicitudCredito(any(SolicitudCreditoDTO.class));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cambioEstatusSolicitudCredito() {
        when(solicitudService.cambioEstatus(any(CambioEstatusDTO.class))).thenReturn(solicitudCredito.getSolicitud());

        ResponseEntity<ResponseDTO> response = solicitudController.cambioEstatusSolicitudCredito(cambioEstatusDTO);

        verify(solicitudService).cambioEstatus(any(CambioEstatusDTO.class));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void dispersarSolicitudCredito() {
        when(solicitudService.dispersarSolicitud(any(DispersionDTO.class))).thenReturn(new Credito());

        ResponseEntity<ResponseDTO> response = solicitudController.dispersarSolicitudCredito(new DispersionDTO());

        verify(solicitudService).dispersarSolicitud(any(DispersionDTO.class));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}