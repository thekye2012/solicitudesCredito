package com.creditos.solicitudes.service;

import com.creditos.solicitudes.entities.Credito;
import com.creditos.solicitudes.repository.CreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditoService {

    private final CreditoRepository creditoRepository;

    public Credito altaCredito(Credito credito) {
        return creditoRepository.save(credito);
    }
}