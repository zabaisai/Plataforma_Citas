package com.plataformacitas.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profesionales")
public class Profesional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String especialidad;

    private String descripcion;

    @OneToOne
    private Usuario usuario;

    public Profesional() {
    }

    public Profesional(Usuario usuario, String especialidad, String descripcion) {
        this.usuario = usuario;
        this.especialidad = especialidad;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}