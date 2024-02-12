package com.creditos.solicitudes.service;

import com.creditos.solicitudes.entities.Cliente;
import com.creditos.solicitudes.entities.Notificacion;
import com.creditos.solicitudes.entities.Solicitud;
import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {
    @Mock
    private NotificacionRepository notificacionRepository;
    @InjectMocks
    private NotificacionService notificacionService;
    private Notificacion notificacion;
    private Cliente cliente;
    private SolicitudCredito solicitudCredito;
    private Solicitud solicitud;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
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
    void enviarNotificacion() {
        when(notificacionRepository.save(any())).thenReturn(notificacion);

        Notificacion notificacionResponse = notificacionService.enviarNotificacion("titulo", "mensaje", solicitudCredito);

        assertNotNull(notificacionResponse);
        assertEquals(notificacion, notificacionResponse);
    }
}