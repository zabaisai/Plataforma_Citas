package com.plataformacitas.presentation.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.plataformacitas.domain.exception.CitaNoDisponibleException;
import com.plataformacitas.domain.exception.DatosInvalidosException;
import com.plataformacitas.domain.exception.RecursoNoEncontradoException;
import com.plataformacitas.domain.exception.UsuarioNoAutorizadoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CitaNoDisponibleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> manejarCitaNoDisponible(CitaNoDisponibleException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(DatosInvalidosException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarDatosInvalidos(DatosInvalidosException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(UsuarioNoAutorizadoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> manejarNoAutorizado(UsuarioNoAutorizadoException ex) {
        return Map.of("error", ex.getMessage());
    }
}