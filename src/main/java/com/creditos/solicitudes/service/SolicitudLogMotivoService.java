package com.creditos.solicitudes.service;


import com.creditos.solicitudes.entities.SolicitudLogMotivo;
import com.creditos.solicitudes.repository.SolicitudLogMotivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolicitudLogMotivoService {

    private final SolicitudLogMotivoRepository solicitudLogMotivoRepository;

    public SolicitudLogMotivo agregar(SolicitudLogMotivo solicitudLogMotivo) {
        return solicitudLogMotivoRepository.save(solicitudLogMotivo);
    }

}