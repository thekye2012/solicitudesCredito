package com.creditos.solicitudes.service;

import com.creditos.solicitudes.entities.SolicitudDispersion;
import com.creditos.solicitudes.repository.SolicitudDispersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolicitudDispersionService {

    private final SolicitudDispersionRepository solicitudDispersionRepository;

    public SolicitudDispersion altaSolicitudDispersion(SolicitudDispersion solicitudDispersion) {
        return solicitudDispersionRepository.save(solicitudDispersion);
    }

}