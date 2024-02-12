package com.creditos.solicitudes.exceptions;

import com.creditos.solicitudes.dto.ResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception ex) {

        ResponseDTO errorResponse = new ResponseDTO(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ErrorAlProcesarSolicitudException.class)
    public ResponseEntity<ResponseDTO> handleErrorAlProcesarSolicitudException(ErrorAlProcesarSolicitudException ex) {

        ResponseDTO errorResponse = new ResponseDTO(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(SolicitudAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO> handleSolicitudAlreadyExistsException(SolicitudAlreadyExistsException ex) {

        ResponseDTO errorResponse = new ResponseDTO(false, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }

    @ExceptionHandler(DatosIncompletosException.class)
    public ResponseEntity<ResponseDTO> handleDatosIncompletosException(DatosIncompletosException ex) {

        ResponseDTO errorResponse = new ResponseDTO(false, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO> handleAccessDenied(AccessDeniedException ex) {
        ResponseDTO response = new ResponseDTO(false, HttpStatus.FORBIDDEN.value(), "Acceso no autorizado");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        ResponseDTO response = new ResponseDTO(false, HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        ResponseDTO response = new ResponseDTO(false, HttpStatus.BAD_REQUEST.value(), "JSON no válido");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDTO> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ResponseDTO response = new ResponseDTO(false, HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ResponseDTO response = new ResponseDTO(false, HttpStatus.CONFLICT.value(), "Error de base de datos: violación de la integridad de los datos" + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> manejarValidacionException(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        ResponseDTO errorResponse = new ResponseDTO(false, HttpStatus.BAD_REQUEST.value(), errores);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SolicitudConErrorException.class)
    public ResponseEntity<ResponseDTO> handleSolicitudConErrorException(SolicitudConErrorException ex) {
        ResponseDTO response = new ResponseDTO(false, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SolicitudNoEncontradaException.class)
    public ResponseEntity<ResponseDTO> handleSolicitudNoEncontradaException(SolicitudNoEncontradaException ex) {
        ResponseDTO response = new ResponseDTO(false, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}