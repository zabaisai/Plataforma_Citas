package com.plataformacitas;

import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void debeCrearUsuarioClienteCorrectamente() {
        Usuario usuario = new Usuario(
                "Carlos Pérez",
                "carlos@email.com",
                "password-encriptado",
                RolUsuario.CLIENTE
        );

        assertEquals("Carlos Pérez", usuario.getNombre());
        assertEquals("carlos@email.com", usuario.getEmail());
        assertEquals("password-encriptado", usuario.getPasswordHash());
        assertEquals(RolUsuario.CLIENTE, usuario.getRol());
        assertTrue(usuario.isActivo());
    }

    @Test
    void debeDesactivarUsuario() {
        Usuario usuario = new Usuario(
                "Ana Gómez",
                "ana@email.com",
                "password-encriptado",
                RolUsuario.CLIENTE
        );

        usuario.desactivar();

        assertFalse(usuario.isActivo());
    }
}