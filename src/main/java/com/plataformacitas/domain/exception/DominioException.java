package com.plataformacitas.domain.exception;

/**
 * Excepción base para errores propios del dominio de la aplicación.
 *
 * Permite representar reglas de negocio incumplidas sin depender
 * de frameworks externos como Spring, JPA o controladores web.
 */
public abstract class DominioException extends RuntimeException {

    private final String codigo;

    protected DominioException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    protected DominioException(String codigo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}