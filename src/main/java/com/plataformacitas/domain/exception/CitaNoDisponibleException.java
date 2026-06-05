package com.plataformacitas.domain.exception;

public class CitaNoDisponibleException extends DominioException {

    private static final String CODIGO = "CITA_NO_DISPONIBLE";

    public CitaNoDisponibleException() {
        super(CODIGO, "La cita no se encuentra disponible en el horario seleccionado.");
    }

    public CitaNoDisponibleException(String mensaje) {
        super(CODIGO, mensaje);
    }

    public CitaNoDisponibleException(String mensaje, Throwable causa) {
        super(CODIGO, mensaje, causa);
    }
}