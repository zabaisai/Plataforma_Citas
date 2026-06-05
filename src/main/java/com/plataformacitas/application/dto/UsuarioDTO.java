package com.plataformacitas.application.dto;

import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.model.Usuario;

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private RolUsuario rol;
    private boolean activo;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String email, RolUsuario rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }

    public static UsuarioDTO desdeEntidad(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.isActivo()
        );
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }
}