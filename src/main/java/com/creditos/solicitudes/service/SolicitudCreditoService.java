package com.creditos.solicitudes.service;


import com.creditos.solicitudes.dto.SolicitudCreditoDTO;
import com.creditos.solicitudes.entities.Cliente;
import com.creditos.solicitudes.entities.Solicitud;
import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.exceptions.ErrorAlProcesarSolicitudException;
import com.creditos.solicitudes.exceptions.SolicitudAlreadyExistsException;
import com.creditos.solicitudes.repository.SolicitudCreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class SolicitudCreditoService {


    private final SolicitudCreditoRepository solicitudCreditoRepository;
    private final ClienteService clienteService;
    private final SolicitudService solicitudService;
    private final SolicitudLogService solicitudLogService;
    private final NotificacionService notificacionService;

    // alta de solicitud de crédito
    public Boolean eliminarSolicitudCredito(Solicitud solicitud) {
        SolicitudCredito solicitudCredito = solicitudCreditoRepository.findBySolicitud(solicitud);
        if (solicitudCredito != null) {
            solicitudCreditoRepository.delete(solicitudCredito);
            // Borrar tambien solicitud
            solicitudService.eliminarSolicitud(solicitud);
            return true;
        }
        return false;
    }

    public SolicitudCredito guardarSolicitudCredito(SolicitudCredito solicitudCredito) {
        return solicitudCreditoRepository.save(solicitudCredito);
    }

    public SolicitudCredito altaSolicitudCredito(@Validated SolicitudCreditoDTO solicitudCreditoDTO) {
        // Si existe algun error, lo guardaremos en solicitudLog

        // Buscamos si idSolicitud existe
        if (Boolean.TRUE.equals(solicitudService.existsByIdSolicitud(solicitudCreditoDTO.getSolicitud().getIdSolicitud()))) {
            throw new SolicitudAlreadyExistsException("Solicitud ID: " + solicitudCreditoDTO.getSolicitud().getIdSolicitud() + " ya existe. No se puede ingresar nuevamente.");
        }
        try {
            Cliente cliente = clienteService.buscarCliente(solicitudCreditoDTO.getCliente().getNombre(), solicitudCreditoDTO.getCliente().getApellidoPaterno(), solicitudCreditoDTO.getCliente().getApellidoMaterno());
            if (cliente == null) {
                cliente = clienteService.altaCliente(solicitudCreditoDTO.getCliente());
            }

            // Asociar cliente y solicitud a solicitud de crédito
            SolicitudCredito solicitudCredito = new SolicitudCredito();
            solicitudCredito.setCliente(cliente);
            solicitudCredito.setEmpresa(solicitudCreditoDTO.getEmpresa());
            solicitudCredito.setPromotor(solicitudCreditoDTO.getPromotor());
            // Guardamos solicitud de crédito
            solicitudCredito = guardarSolicitudCredito(solicitudCredito);

            Solicitud solicitud = new Solicitud();

            solicitud.setIdSolicitud(solicitudCreditoDTO.getSolicitud().getIdSolicitud());
            solicitud.setMonto(solicitudCreditoDTO.getSolicitud().getMonto());
            solicitud.setProducto(solicitudCreditoDTO.getSolicitud().getProducto());
            solicitud.setTipoSolicitudStr(solicitudCreditoDTO.getSolicitud().getTipoSolicitudStr());
            solicitud.setIdTipoSolicitud(solicitudCreditoDTO.getSolicitud().getIdTipoSolicitud());
            solicitud.setTasa(solicitudCreditoDTO.getSolicitud().getTasa());
            solicitud.setPlazo(solicitudCreditoDTO.getSolicitud().getPlazo());
            solicitud.setFrecuencia(solicitudCreditoDTO.getSolicitud().getFrecuencia());
            solicitud.setFechaSolicitud(solicitudCreditoDTO.getSolicitud().getFechaSolicitud());
            solicitud.setIdEstatus("INGRESADO");
            solicitud.setSolicitudCredito(solicitudCredito);
            solicitud = solicitudService.altaSolicitud(solicitud);

            solicitudCredito.setCliente(cliente);
            solicitudCredito.setSolicitud(solicitud);
            // Guardamos solicitud de crédito
            solicitudCredito = guardarSolicitudCredito(solicitudCredito);

            // Agregar estatus a la solicitud como "INGRESADO"
            solicitudLogService.agregar(solicitudCredito, "INGRESADO", java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")), null);

            notificacionService.enviarNotificacion("Solicitud de crédito", "Se ingreso una solicitud de credito", solicitudCredito);


            return solicitudCredito;
        } catch (Exception e) {
            // Si existe problemas y ya se guardo solicitudCredito, lo eliminamos y guardamos en solicitudLog
            // Buscamos si idSolicitud existe en Solicitud
            Solicitud solicitud = solicitudService.buscarSolicitud(solicitudCreditoDTO.getSolicitud().getIdSolicitud());
            // Eliminamos solicitudCredito
            if (solicitud != null) {
                eliminarSolicitudCredito(solicitud);
            }

            throw new ErrorAlProcesarSolicitudException("Error al procesar solicitud de crédito");
        }


    }

}