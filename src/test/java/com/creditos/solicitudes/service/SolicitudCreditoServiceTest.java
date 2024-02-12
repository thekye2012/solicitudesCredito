package com.creditos.solicitudes.service;

import com.creditos.solicitudes.dto.ClienteDTO;
import com.creditos.solicitudes.dto.SolicitudCreditoDTO;
import com.creditos.solicitudes.dto.SolicitudDTO;
import com.creditos.solicitudes.entities.Cliente;
import com.creditos.solicitudes.entities.Solicitud;
import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.exceptions.ErrorAlProcesarSolicitudException;
import com.creditos.solicitudes.exceptions.SolicitudAlreadyExistsException;
import com.creditos.solicitudes.repository.SolicitudCreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudCreditoServiceTest {
    @Mock
    private SolicitudCreditoRepository solicitudCreditoRepository;
    @Mock
    private ClienteService clienteService;
    @Mock
    private SolicitudService solicitudService;
    @Mock
    private SolicitudLogService solicitudLogService;
    @Mock
    private NotificacionService notificacionService;
    @InjectMocks
    private SolicitudCreditoService solicitudCreditoService;

    // Variables de prueba
    private ClienteDTO clienteDTO;
    private SolicitudDTO solicitudDTO;
    private SolicitudCreditoDTO solicitudCreditoDTO;
    private Cliente cliente;
    private SolicitudCredito solicitudCredito;
    private Solicitud solicitud;

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


    }

    @Test
    void altaSolicitudCreditoExito() {
        when(clienteService.buscarCliente(anyString(), anyString(), anyString())).thenReturn(cliente);
        when(solicitudService.existsByIdSolicitud(anyString())).thenReturn(false);
        when(solicitudCreditoRepository.save(any(SolicitudCredito.class))).thenReturn(solicitudCredito);
        when(solicitudService.altaSolicitud(any(Solicitud.class))).thenReturn(solicitud);
        when(solicitudLogService.agregar(any(SolicitudCredito.class), anyString(), anyString(), any())).thenReturn(null);
        when(notificacionService.enviarNotificacion(anyString(), anyString(), any())).thenReturn(null);

        SolicitudCredito solicitudCreditoRetornada = solicitudCreditoService.altaSolicitudCredito(solicitudCreditoDTO);

        verify(clienteService).buscarCliente(anyString(), anyString(), anyString());
        verify(solicitudService).existsByIdSolicitud(anyString());
        verify(solicitudCreditoRepository, times(2)).save(any(SolicitudCredito.class));
        verify(solicitudService).altaSolicitud(any(Solicitud.class));
        verify(solicitudLogService).agregar(any(SolicitudCredito.class), anyString(), anyString(), any());
        verify(notificacionService).enviarNotificacion(anyString(), anyString(), any());

        assertNotNull(solicitudCreditoRetornada); // Verifica que el método te de una instancia en lugar de un nulo
        assertEquals(solicitudCredito, solicitudCreditoRetornada); // Comprueba que la solicitud de credito que te regresa, sea la misma que la que se guardo
        assertEquals(1L, solicitudCreditoRetornada.getSolicitud().getId()); //Comprueba que la solicitud que te regresa, el código de solicitud coincida
        assertEquals(1L, solicitudCreditoRetornada.getCliente().getIdCliente()); //Comprueba que la solicitud que te regresa, el código de solicitud coincida
    }

    @Test
    void altaSolicitudCreditoErrorSolicitudYaExiste() {

        when(solicitudService.existsByIdSolicitud(anyString())).thenReturn(true);

        try {
            solicitudCreditoService.altaSolicitudCredito(solicitudCreditoDTO);
        } catch (SolicitudAlreadyExistsException e) {
            assertEquals("Solicitud ID: 110102044798 ya existe. No se puede ingresar nuevamente.", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }


    }

    @Test
    void altaSolicitudCreditoErrorClienteNoEncontrado() {
        // Cliente no encontrado, pero se dara de alta
        when(clienteService.buscarCliente(anyString(), anyString(), anyString())).thenReturn(null);
        when(clienteService.altaCliente(any(ClienteDTO.class))).thenReturn(cliente);
        when(solicitudService.existsByIdSolicitud(anyString())).thenReturn(false);
        when(solicitudCreditoRepository.save(any(SolicitudCredito.class))).thenReturn(solicitudCredito);
        when(solicitudService.altaSolicitud(any(Solicitud.class))).thenReturn(solicitud);
        when(solicitudLogService.agregar(any(SolicitudCredito.class), anyString(), anyString(), any())).thenReturn(null);
        when(notificacionService.enviarNotificacion(anyString(), anyString(), any())).thenReturn(null);

        SolicitudCredito solicitudCreditoRetornada = solicitudCreditoService.altaSolicitudCredito(solicitudCreditoDTO);

        verify(clienteService).buscarCliente(anyString(), anyString(), anyString());
        verify(solicitudService).existsByIdSolicitud(anyString());
        verify(solicitudCreditoRepository, times(2)).save(any(SolicitudCredito.class));
        verify(solicitudService).altaSolicitud(any(Solicitud.class));
        verify(solicitudLogService).agregar(any(SolicitudCredito.class), anyString(), anyString(), any());
        verify(notificacionService).enviarNotificacion(anyString(), anyString(), any());

        assertNotNull(solicitudCreditoRetornada); // Verifica que el método te de una instancia en lugar de un nulo
        assertEquals(solicitudCredito, solicitudCreditoRetornada); // Comprueba que la solicitud de credito que te regresa, sea la misma que la que se guardo
        assertEquals(1L, solicitudCreditoRetornada.getSolicitud().getId()); //Comprueba que la solicitud que te regresa, el código de solicitud coincida
        assertEquals(1L, solicitudCreditoRetornada.getCliente().getIdCliente()); //Comprueba que la solicitud que te regresa, el código de solicitud coincida

    }

    @Test
    void altaSolicitudCreditoErrorAlGuardarSolicitudCredito() {
        // Si ocurre un error al guardar la solicitud de credito, se debe lanzar una excepcion
        // Detecta que la solicitud de credito no se guardo correctamente


        when(solicitudService.buscarSolicitud(anyString())).thenReturn(null);
        try {
            solicitudCreditoService.altaSolicitudCredito(solicitudCreditoDTO);
        } catch (ErrorAlProcesarSolicitudException e) {
            assertEquals("Error al procesar solicitud de crédito", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }


    }

    @Test
    void altaSolicitudCreditoErrorSolicitudGuardadaPeroOcurrioUnError() {
        // Si se guardo la solicitud pero ocurrio un error despues de guardarla (por ejemplo, al enviar notificacion)
        // se debe eliminar la solicitud guardada y lanzar una excepcion
        when(solicitudService.buscarSolicitud(anyString())).thenReturn(solicitud);
        try {
            solicitudCreditoService.altaSolicitudCredito(solicitudCreditoDTO);
        } catch (ErrorAlProcesarSolicitudException e) {
            assertEquals("Error al procesar solicitud de crédito", e.getMessage());
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getClass().getName());
        }
    }

}