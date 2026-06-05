package com.plataformacitas.presentation.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.plataformacitas.domain.exception.CitaNoDisponibleException;
import com.plataformacitas.domain.exception.DominioException;
import com.plataformacitas.domain.exception.HorarioNoDisponibleException;
import com.plataformacitas.domain.exception.RecursoNoEncontradoException;
import com.plataformacitas.domain.exception.UsuarioNoAutorizadoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return respuestaError(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioNoAutorizadoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> manejarNoAutorizado(UsuarioNoAutorizadoException ex) {
        return respuestaError(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            CitaNoDisponibleException.class,
            HorarioNoDisponibleException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> manejarConflictosDeAgenda(DominioException ex) {
        return respuestaError(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DominioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> manejarErrorDeDominio(DominioException ex) {
        return respuestaError(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> manejarErrorGeneral(Exception ex) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "ERROR_INTERNO",
                "mensaje", "Ocurrió un error inesperado en el sistema."
        );
    }

    private Map<String, Object> respuestaError(DominioException ex, HttpStatus status) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", ex.getCodigo(),
                "mensaje", ex.getMessage()
        );
    }
}