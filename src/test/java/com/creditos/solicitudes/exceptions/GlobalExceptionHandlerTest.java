package com.creditos.solicitudes.exceptions;

import com.creditos.solicitudes.dto.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
    }

    @Test
    void handleException() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleException(new Exception("Error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void handleErrorAlProcesarSolicitudException() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleErrorAlProcesarSolicitudException(new ErrorAlProcesarSolicitudException("Error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void handleSolicitudAlreadyExistsException() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleSolicitudAlreadyExistsException(new SolicitudAlreadyExistsException("Error"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleDatosIncompletosException() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleDatosIncompletosException(new DatosIncompletosException("Error"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleAccessDenied() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleAccessDenied(new AccessDeniedException("Error"));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void handleResourceNotFound() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleResourceNotFound(new ResourceNotFoundException("Error"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleMessageNotReadable() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleMessageNotReadable(new HttpMessageNotReadableException("Error"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleMethodNotSupported() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleMethodNotSupported(new HttpRequestMethodNotSupportedException("Error"));
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void handleDataIntegrityViolation() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleDataIntegrityViolation(new DataIntegrityViolationException("Error"));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }


    @Test
    void handleSolicitudConErrorException() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleSolicitudConErrorException(new SolicitudConErrorException("Error"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleSolicitudNoEncontradaException() {
        ResponseEntity<ResponseDTO> response = globalExceptionHandler.handleSolicitudNoEncontradaException(new SolicitudNoEncontradaException("Error"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
}