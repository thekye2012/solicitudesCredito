package com.creditos.solicitudes.exceptions;

public class SolicitudAlreadyExistsException extends RuntimeException {
    public SolicitudAlreadyExistsException(String message) {
        super(message);
    }
}