package com.creditos.solicitudes.service;

import com.creditos.solicitudes.dto.CambioEstatusMotivoDTO;
import com.creditos.solicitudes.entities.*;
import com.creditos.solicitudes.repository.SolicitudLogMotivoRepository;
import com.creditos.solicitudes.repository.SolicitudLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitudLogServiceTest {
    @Mock
    private SolicitudLogMotivo solicitudLogMotivo;
    @Mock
    private SolicitudLogRepository solicitudLogRepository;
    @Mock
    private SolicitudLogMotivoService solicitudLogMotivoService;
    @Mock
    private SolicitudLogMotivoRepository solicitudLogMotivoRepository;
    @InjectMocks
    private SolicitudLogService solicitudLogService;
    private SolicitudCredito solicitudCredito;
    private SolicitudLog solicitudLog;
    private CambioEstatusMotivoDTO cambioEstatusMotivoDTO;
    private Cliente cliente;
    private Solicitud solicitud;

    @BeforeEach
    void setUp() {
        cambioEstatusMotivoDTO = new CambioEstatusMotivoDTO();
        cambioEstatusMotivoDTO.setCodigo("123");
        cambioEstatusMotivoDTO.setDescripcion("Documentos incompletos");

        cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setNombre("PERLA TOMASA");
        cliente.setApellidoPaterno("CABRERA");
        cliente.setApellidoMaterno("ROMAN");

        solicitudCredito = new SolicitudCredito();
        solicitudCredito.setIdSolicitudCredito(1L);
        solicitudCredito.setPromotor("SLP34/CURP");
        solicitudCredito.setEmpresa("XXXXX");


        solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setIdSolicitud("110102044798");
        solicitud.setMonto(167000);
        solicitud.setProducto("IMSS");
        solicitud.setTipoSolicitudStr("CREDITO NUEVO");
        solicitud.setIdTipoSolicitud("394");
        solicitud.setTasa(39);
        solicitud.setPlazo(0);
        solicitud.setFrecuencia("Semanal/Mensual/Catorcenal");
        solicitud.setFechaSolicitud("20220727");
        solicitud.setIdEstatus("INGRESADA");
        solicitud.setSolicitudCredito(solicitudCredito);

        solicitudLog = new SolicitudLog();
        solicitudLog.setIdSolicitudLog(1L);
        solicitudLog.setSolicitudCredito(solicitudCredito);
        solicitudLog.setIdEstatus("INGRESADA");
        solicitudLog.setFechaCambio("20220727");
        solicitudLog.setSolicitudLogMotivos(List.of(solicitudLogMotivo));


    }

    @Test
    void agregar() {
        when(solicitudLogRepository.save(any(SolicitudLog.class))).thenReturn(solicitudLog);

        SolicitudLog solicitudLogResponse = solicitudLogService.agregar(solicitudCredito, solicitud.getIdEstatus(), solicitud.getFechaSolicitud(), List.of(cambioEstatusMotivoDTO));

        verify(solicitudLogRepository).save(any(SolicitudLog.class));

        assertNotNull(solicitudLogResponse);
        assertEquals(solicitudLog, solicitudLogResponse);

    }

    @Test
    void existeSolicitudLogConEstatus() {
        when(solicitudLogRepository.existsBySolicitudCreditoAndIdEstatus(solicitudCredito, solicitud.getIdEstatus())).thenReturn(true);

        Boolean existeSolicitudLogConEstatus = solicitudLogService.existeSolicitudLogConEstatus(solicitudCredito, solicitud.getIdEstatus());

        verify(solicitudLogRepository).existsBySolicitudCreditoAndIdEstatus(solicitudCredito, solicitud.getIdEstatus());

        assertNotNull(existeSolicitudLogConEstatus);
        assertEquals(true, existeSolicitudLogConEstatus);
    }
}