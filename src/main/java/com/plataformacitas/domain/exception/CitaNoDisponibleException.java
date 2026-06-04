package com.plataformacitas.domain.exception;

public class CitaNoDisponibleException extends RuntimeException {

    public CitaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}