package com.creditos.solicitudes.service;

import com.creditos.solicitudes.dto.CambioEstatusMotivoDTO;
import com.creditos.solicitudes.entities.SolicitudCredito;
import com.creditos.solicitudes.entities.SolicitudLog;
import com.creditos.solicitudes.entities.SolicitudLogMotivo;
import com.creditos.solicitudes.repository.SolicitudLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudLogService {

    private final SolicitudLogMotivoService solicitudLogMotivoService;
    private final SolicitudLogRepository solicitudLogRepository;


    public SolicitudLog agregar(SolicitudCredito solicitudCredito, String estatus, String fechaCambio, List<CambioEstatusMotivoDTO> solicitudLogMotivos) {
        SolicitudLog solicitudLog = new SolicitudLog();
        solicitudLog.setSolicitudCredito(solicitudCredito);
        solicitudLog.setIdEstatus(estatus);
        solicitudLog.setFechaCambio(fechaCambio);
        solicitudLog = solicitudLogRepository.save(solicitudLog);

        if (solicitudLogMotivos != null) {
            for (CambioEstatusMotivoDTO cambioEstatusMotivoDTO : solicitudLogMotivos) {
                SolicitudLogMotivo solicitudLogMotivo = new SolicitudLogMotivo();
                solicitudLogMotivo.setSolicitudLog(solicitudLog);
                solicitudLogMotivo.setCodigo(cambioEstatusMotivoDTO.getCodigo());
                solicitudLogMotivo.setDescripcion(cambioEstatusMotivoDTO.getDescripcion());
                solicitudLogMotivoService.agregar(solicitudLogMotivo);
            }
        }
        return solicitudLog;
    }

    public Boolean existeSolicitudLogConEstatus(SolicitudCredito solicitudCredito, String estatus) {
        return solicitudLogRepository.existsBySolicitudCreditoAndIdEstatus(solicitudCredito, estatus);
    }
}