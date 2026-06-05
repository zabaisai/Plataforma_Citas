package com.plataformacitas.domain.exception;

public class UsuarioNoAutorizadoException extends DominioException {

    private static final String CODIGO = "USUARIO_NO_AUTORIZADO";

    public UsuarioNoAutorizadoException() {
        super(CODIGO, "El usuario no está autorizado para realizar esta acción.");
    }

    public UsuarioNoAutorizadoException(String mensaje) {
        super(CODIGO, mensaje);
    }

    public UsuarioNoAutorizadoException(String mensaje, Throwable causa) {
        super(CODIGO, mensaje, causa);
    }
}