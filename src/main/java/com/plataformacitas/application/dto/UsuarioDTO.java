package com.plataformacitas.application.dto;

import com.plataformacitas.domain.enums.RolUsuario;

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private RolUsuario rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String email, RolUsuario rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
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
}