package com.plataformacitas.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefono;

    @OneToOne
    private Usuario usuario;

    public Cliente() {
    }

    public Cliente(Usuario usuario, String telefono) {
        this.usuario = usuario;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}