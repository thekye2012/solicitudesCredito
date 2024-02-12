package com.creditos.solicitudes.service;

import com.creditos.solicitudes.entities.Credito;
import com.creditos.solicitudes.repository.CreditoRepository;
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
class CreditoServiceTest {
    @Mock
    private CreditoRepository creditoRepository;
    @InjectMocks
    private CreditoService creditoService;
    private Credito credito;

    @BeforeEach
    void setUp() {
        credito = new Credito();

    }

    @Test
    void altaCredito() {
        when(creditoRepository.save(any())).thenReturn(credito);

        Credito creditoResponse = creditoService.altaCredito(credito);

        assertNotNull(creditoResponse);
        assertEquals(credito, creditoResponse);
    }
}