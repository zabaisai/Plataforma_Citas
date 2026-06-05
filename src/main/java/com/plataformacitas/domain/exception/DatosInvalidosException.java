package com.plataformacitas.domain.exception;

public class DatosInvalidosException extends DominioException {

    private static final String CODIGO = "DATOS_INVALIDOS";

    public DatosInvalidosException() {
        super(CODIGO, "Los datos ingresados no son válidos.");
    }

    public DatosInvalidosException(String mensaje) {
        super(CODIGO, mensaje);
    }

    public DatosInvalidosException(String mensaje, Throwable causa) {
        super(CODIGO, mensaje, causa);
    }
}