package com.plataformacitas.domain.exception;

public class HorarioNoDisponibleException extends DominioException {

    private static final String CODIGO = "HORARIO_NO_DISPONIBLE";

    public HorarioNoDisponibleException() {
        super(CODIGO, "El profesional no tiene horario disponible para la fecha y hora seleccionadas.");
    }

    public HorarioNoDisponibleException(String mensaje) {
        super(CODIGO, mensaje);
    }

    public HorarioNoDisponibleException(String mensaje, Throwable causa) {
        super(CODIGO, mensaje, causa);
    }
}