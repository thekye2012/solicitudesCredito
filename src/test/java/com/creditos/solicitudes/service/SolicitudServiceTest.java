package com.creditos.solicitudes.service;

import com.creditos.solicitudes.dto.CambioEstatusDTO;
import com.creditos.solicitudes.dto.CambioEstatusMotivoDTO;
import com.creditos.solicitudes.dto.DispersionDTO;
import com.creditos.solicitudes.entities.*;
import com.creditos.solicitudes.exceptions.SolicitudNoEncontradaException;
import com.creditos.solicitudes.repository.SolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepository;
    @Mock
    private SolicitudLogService solicitudLogService;
    @Mock
    private NotificacionService notificacionService;
    @Mock
    private CreditoService creditoService;
    @Mock
    private SolicitudDispersionService solicitudDispersionService;

    @InjectMocks
    private SolicitudService solicitudService;
    private Cliente cliente;
    private SolicitudCredito solicitudCredito;
    private Solicitud solicitud;
    private CambioEstatusMotivoDTO cambioEstatusMotivoDTO;
    private CambioEstatusDTO cambioEstatusDTO;
    private DispersionDTO dispersionDTO;
    private Credito credito;
    private SolicitudDispersion solicitudDispersion;

    @BeforeEach
    void setUp() {
        cambioEstatusMotivoDTO = new CambioEstatusMotivoDTO();
        cambioEstatusMotivoDTO.setCodigo("123");
        cambioEstatusMotivoDTO.setDescripcion("Documentos incompletos");

        cambioEstatusDTO = new CambioEstatusDTO();
        cambioEstatusDTO.setIdEstatus("APROB");
        cambioEstatusDTO.setIdSolicitud("110102044798");
        cambioEstatusDTO.setFechaCambio("20220727");
        cambioEstatusDTO.setMotivo(List.of(cambioEstatusMotivoDTO));

        cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setNombre("PERLA TOMASA");
        cliente.setApellidoPaterno("CABRERA");
        cliente.setApellidoMaterno("ROMAN");

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

        solicitudCredito = new SolicitudCredito();
        solicitudCredito.setIdSolicitudCredito(1L);
        solicitudCredito.setPromotor("SLP34/CURP");
        solicitudCredito.setEmpresa("XXXXX");
        solicitudCredito.setCliente(cliente);

        solicitud.setSolicitudCredito(solicitudCredito);

        dispersionDTO = new DispersionDTO();
        dispersionDTO.setIdSolicitud("110102044798");
        dispersionDTO.setIdCredito(1234L);
        dispersionDTO.setCapitalDispersado(100.50);
        dispersionDTO.setMonto(100.50);
        dispersionDTO.setTasa(40.35);
        dispersionDTO.setPlazo(30);
        dispersionDTO.setFrecuencia("semanal");

        credito = new Credito();
        credito.setSolicitudCredito(solicitudCredito);
        credito.setCliente(cliente);
        credito.setIdCredito(1234L);
        credito.setCapitalDispersado(100.50);
        credito.setMonto(100.50);
        credito.setTasa(40.35);
        credito.setPlazo(30);
        credito.setFrecuencia("semanal");
        credito.setFechaCredito(LocalDate.now());

        solicitudDispersion = new SolicitudDispersion();
        solicitudDispersion.setIdSolicitudDispersion(1L);
        solicitudDispersion.setSolicitudCredito(solicitudCredito);
        solicitudDispersion.setCapitalDispersado(100.50);
        solicitudDispersion.setMonto(100.50);
        solicitudDispersion.setTasa(40.35);
        solicitudDispersion.setPlazo(30);
        solicitudDispersion.setFrecuencia("semanal");
        solicitudDispersion.setCredito(credito);
        solicitudDispersion.setFechaDispersion(LocalDate.now());

    }

    @Test
    void cambioEstatus() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        when(solicitudLogService.existeSolicitudLogConEstatus(any(SolicitudCredito.class), anyString())).thenReturn(false);
        when(solicitudRepository.save(solicitud)).thenReturn(solicitud);
        when(notificacionService.enviarNotificacion(anyString(), anyString(), any(SolicitudCredito.class))).thenReturn(any(Notificacion.class));

        Solicitud solicitudRetornada = solicitudService.cambioEstatus(cambioEstatusDTO);

        verify(solicitudRepository).findByIdSolicitud(anyString());
        verify(solicitudLogService).existeSolicitudLogConEstatus(any(SolicitudCredito.class), anyString());
        verify(solicitudRepository).save(solicitud);
        verify(notificacionService).enviarNotificacion(anyString(), anyString(), any(SolicitudCredito.class));

        assertNotNull(solicitudRetornada);
        assertEquals("APROB", solicitudRetornada.getIdEstatus());

    }

    @Test
    void dispersarSolicitud() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        when(solicitudRepository.save(solicitud)).thenReturn(solicitud);
        when(creditoService.altaCredito(any())).thenReturn(credito);
        when(solicitudDispersionService.altaSolicitudDispersion(any())).thenReturn(solicitudDispersion);

        Credito creditoRetornada = solicitudService.dispersarSolicitud(dispersionDTO);

        verify(solicitudRepository).findByIdSolicitud(anyString());
        verify(solicitudRepository).save(solicitud);
        verify(creditoService).altaCredito(any());
        verify(solicitudDispersionService).altaSolicitudDispersion(any());

        assertNotNull(creditoRetornada);
        assertEquals(1234L, creditoRetornada.getIdCredito());
    }

    @Test
    void buscarSolicitudTest() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        Solicitud solicitudRetornada = solicitudService.buscarSolicitud("110102044798");
        verify(solicitudRepository).findByIdSolicitud("110102044798");
        assertNotNull(solicitudRetornada);
        assertEquals("110102044798", solicitudRetornada.getIdSolicitud());
    }

    @Test
    void buscarSolicitudNoEncontradaTest() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(null);
        Solicitud solicitudRetornada = solicitudService.buscarSolicitud("110102044798");
        verify(solicitudRepository).findByIdSolicitud("110102044798");
        assertNull(solicitudRetornada);
    }

    @Test
    void altaSolicitud() {
        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitud);
        Solicitud solicitudRetornada = solicitudService.altaSolicitud(solicitud);
        verify(solicitudRepository).save(any(Solicitud.class));
        assertNotNull(solicitudRetornada);
        assertEquals(solicitud, solicitudRetornada);
    }

    @Test
    void existsByIdSolicitud() {
        when(solicitudRepository.existsByIdSolicitud(anyString())).thenReturn(true);
        Boolean existsByIdSolicitud = solicitudService.existsByIdSolicitud("110102044798");
        verify(solicitudRepository).existsByIdSolicitud("110102044798");
        assertNotNull(existsByIdSolicitud);
        assertEquals(true, existsByIdSolicitud);
    }

    @Test
    void cambioEstatusSolicitudNoEncontradaException() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(null);
        try {
            solicitudService.cambioEstatus(cambioEstatusDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void cambioEstatusSolicitudConErrorException() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        when(solicitudLogService.existeSolicitudLogConEstatus(any(SolicitudCredito.class), anyString())).thenReturn(true);
        try {
            solicitudService.cambioEstatus(cambioEstatusDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void cambioEstatusErrorYaTieneEstatus() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        cambioEstatusDTO.setIdEstatus("INGRESADA");
        try {
            solicitudService.cambioEstatus(cambioEstatusDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void cambioEstatusErrorEstatusActualEsError() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        solicitud.setIdEstatus("ERROR");
        when(solicitudLogService.agregar(any(SolicitudCredito.class), anyString(), anyString(), any())).thenReturn(null);
        try {
            solicitudService.cambioEstatus(cambioEstatusDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void cambioEstatusErrorEstatusActualEsDispersion() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        solicitud.setIdEstatus("DISPERSADO");
        try {
            solicitudService.cambioEstatus(cambioEstatusDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void cambioEstatusErrorAlCambiarEstatus() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        when(solicitudRepository.save(solicitud)).thenThrow(new RuntimeException());
        try {
            solicitudService.cambioEstatus(cambioEstatusDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void dispersarSolicitudSolicitudNoEncontradaException() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(null);
        try {
            solicitudService.dispersarSolicitud(dispersionDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void dispersarSolicitudErrorYaDispersada() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        solicitud.setIdEstatus("DISPERSADO");
        try {
            solicitudService.dispersarSolicitud(dispersionDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Solicitud no encontrada", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void dispersarSolicitudErrorNosePuedeDispersar() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        solicitud.setIdEstatus("ERROR");
        try {
            solicitudService.dispersarSolicitud(dispersionDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("No se puede dispersar la solicitud, debido que tiene un estatus de ERROR.", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

    @Test
    void dispersarSolicitudErrorAlDispersar() {
        when(solicitudRepository.findByIdSolicitud(anyString())).thenReturn(solicitud);
        when(solicitudRepository.save(solicitud)).thenThrow(new RuntimeException());
        try {
            solicitudService.dispersarSolicitud(dispersionDTO);
        } catch (SolicitudNoEncontradaException e) {
            assertEquals("Error al dispersar la solicitud", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }
}