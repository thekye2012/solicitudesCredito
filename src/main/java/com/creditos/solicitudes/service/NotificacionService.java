package com.creditos.solicitudes.service;


import com.creditos.solicitudes.entities.Notificacion;
import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;


    public Notificacion enviarNotificacion(String titulo, String mensaje, SolicitudCredito solicitudCredito) {
        Notificacion notificacion = new Notificacion();
        notificacion.setPromotor(solicitudCredito.getPromotor());
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setSolicitudCredito(solicitudCredito);
        notificacion.setLeido(false);
        notificacion.setFechaNotificacion(java.time.LocalDateTime.now());

        return notificacionRepository.save(notificacion);
    }
}