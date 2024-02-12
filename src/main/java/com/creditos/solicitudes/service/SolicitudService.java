package com.creditos.solicitudes.service;


import com.creditos.solicitudes.dto.CambioEstatusDTO;
import com.creditos.solicitudes.dto.DispersionDTO;
import com.creditos.solicitudes.entities.Credito;
import com.creditos.solicitudes.entities.Solicitud;
import com.creditos.solicitudes.entities.SolicitudDispersion;
import com.creditos.solicitudes.exceptions.SolicitudConErrorException;
import com.creditos.solicitudes.exceptions.SolicitudNoEncontradaException;
import com.creditos.solicitudes.repository.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SolicitudService {

    public static final String ERROR_CODE = "ERROR";
    public static final String DISPERSADO_CODE = "DISPERSADO";
    private final SolicitudRepository solicitudRepository;
    private final SolicitudLogService solicitudLogService;
    private final NotificacionService notificacionService;
    private final CreditoService creditoService;
    private final SolicitudDispersionService solicitudDispersionService;

    public Solicitud buscarSolicitud(String idSolicitud) {
        return solicitudRepository.findByIdSolicitud(idSolicitud);
    }

    public Solicitud altaSolicitud(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    public Boolean existsByIdSolicitud(String idSolicitud) {
        return solicitudRepository.existsByIdSolicitud(idSolicitud);
    }

    public Boolean eliminarSolicitud(Solicitud solicitud) {
        solicitudRepository.delete(solicitud);
        return true;
    }

    public Solicitud cambioEstatus(CambioEstatusDTO cambioEstatusDTO) {
        Solicitud solicitud = solicitudRepository.findByIdSolicitud(cambioEstatusDTO.getIdSolicitud());

        if (solicitud == null) {
            throw new SolicitudNoEncontradaException("Solicitud no encontrada");
        }

        // Si existe, no se puede cambiar el estatus
        if (Boolean.TRUE.equals(solicitudLogService.existeSolicitudLogConEstatus(solicitud.getSolicitudCredito(), ERROR_CODE))) {
            throw new SolicitudConErrorException("No se puede cambiar el estatus de la solicitud, debido que tiene un estatus de ERROR.");
        }
        if (solicitud.getIdEstatus().equals(cambioEstatusDTO.getIdEstatus())) {
            throw new SolicitudConErrorException("La solicitud ya tiene el estatus " + cambioEstatusDTO.getIdEstatus() + ".");
        }
        if (solicitud.getIdEstatus().equals(ERROR_CODE)) {
            // Si el estatus actual es "ERROR", se agrega un nuevo ERROR a SolicitudLog para mantener registro de solicitudes
            solicitudLogService.agregar(solicitud.getSolicitudCredito(), ERROR_CODE, cambioEstatusDTO.getFechaCambio(), null);
            throw new SolicitudConErrorException("No se puede cambiar el estatus de la solicitud, debido que tiene un estatus de ERROR.");
        }
        if (solicitud.getIdEstatus().equals(DISPERSADO_CODE)) {
            throw new SolicitudConErrorException("No se puede cambiar el estatus de la solicitud, debido que ya tiene un estatus de DISPERSADO.");
        }
        try {
            solicitud.setIdEstatus(cambioEstatusDTO.getIdEstatus());
            solicitud = solicitudRepository.save(solicitud);
            solicitudLogService.agregar(solicitud.getSolicitudCredito(), cambioEstatusDTO.getIdEstatus(), cambioEstatusDTO.getFechaCambio(), cambioEstatusDTO.getMotivo());
            notificacionService.enviarNotificacion("Solicitud cambio de estatus", "Se ha cambiado el estatus de la solicitud " + solicitud.getIdSolicitud() + " a " + cambioEstatusDTO.getIdEstatus(), solicitud.getSolicitudCredito());
            return solicitud;
        } catch (Exception e) {
            solicitudLogService.agregar(solicitud.getSolicitudCredito(), ERROR_CODE, cambioEstatusDTO.getFechaCambio(), null);
            solicitud.setIdEstatus(ERROR_CODE);
            solicitudRepository.save(solicitud);
            throw new SolicitudConErrorException("Error al cambiar el estatus de la solicitud");
        }
    }

    public Credito dispersarSolicitud(DispersionDTO dispersionDTO) {
        Solicitud solicitud = solicitudRepository.findByIdSolicitud(dispersionDTO.getIdSolicitud());
        if (solicitud == null) {
            throw new SolicitudNoEncontradaException("Solicitud no encontrada");
        }
        if (solicitud.getIdEstatus().equals(DISPERSADO_CODE)) {
            throw new SolicitudConErrorException("La solicitud ya se encuentra dispersada.");
        }
        if (solicitud.getIdEstatus().equals(ERROR_CODE)) {
            throw new SolicitudConErrorException("No se puede dispersar la solicitud, debido que tiene un estatus de ERROR.");
        }
        try {
            solicitud.setIdEstatus(DISPERSADO_CODE);
            solicitudRepository.save(solicitud);

            // alta de crédito
            Credito credito = new Credito();
            credito.setSolicitudCredito(solicitud.getSolicitudCredito());
            credito.setCliente(solicitud.getSolicitudCredito().getCliente());

            credito.setIdCredito(dispersionDTO.getIdCredito());
            credito.setCapitalDispersado(dispersionDTO.getCapitalDispersado());
            credito.setMonto(dispersionDTO.getMonto());
            credito.setPlazo(dispersionDTO.getPlazo());
            credito.setTasa(dispersionDTO.getTasa());
            credito.setFrecuencia(dispersionDTO.getFrecuencia());
            credito.setFechaCredito(LocalDate.now());

            credito = creditoService.altaCredito(credito);

            SolicitudDispersion solicitudDispersion = new SolicitudDispersion();
            solicitudDispersion.setSolicitudCredito(solicitud.getSolicitudCredito());
            solicitudDispersion.setCapitalDispersado(dispersionDTO.getCapitalDispersado());
            solicitudDispersion.setMonto(dispersionDTO.getMonto());
            solicitudDispersion.setTasa(dispersionDTO.getTasa());
            solicitudDispersion.setPlazo(dispersionDTO.getPlazo());
            solicitudDispersion.setFrecuencia(dispersionDTO.getFrecuencia());
            solicitudDispersion.setCredito(credito);
            solicitudDispersion.setFechaDispersion(LocalDate.now());

            solicitudDispersionService.altaSolicitudDispersion(solicitudDispersion);


            solicitudLogService.agregar(solicitud.getSolicitudCredito(), DISPERSADO_CODE, java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")), null);
            notificacionService.enviarNotificacion("Solicitud dispersada", "Se ha dispersado la solicitud " + solicitud.getIdSolicitud() + ", comision de dispersión: $" + dispersionDTO.getCapitalDispersado(), solicitud.getSolicitudCredito());

            return credito;
        } catch (Exception e) {
            solicitudLogService.agregar(solicitud.getSolicitudCredito(), ERROR_CODE, java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")), null);
            solicitud.setIdEstatus(ERROR_CODE);
            solicitudRepository.save(solicitud);
            throw new SolicitudConErrorException("Error al dispersar la solicitud");
        }
    }

}