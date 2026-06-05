package com.plataformacitas.domain.exception;

public class RecursoNoEncontradoException extends DominioException {

    private static final String CODIGO = "RECURSO_NO_ENCONTRADO";

    public RecursoNoEncontradoException() {
        super(CODIGO, "El recurso solicitado no fue encontrado.");
    }

    public RecursoNoEncontradoException(String mensaje) {
        super(CODIGO, mensaje);
    }

    public RecursoNoEncontradoException(String mensaje, Throwable causa) {
        super(CODIGO, mensaje, causa);
    }
}