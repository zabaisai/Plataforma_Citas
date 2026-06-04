package com.plataformacitas.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private Integer duracionMinutos;

    private Double precio;

    private boolean activo = true;

    public Servicio() {
    }

    public Servicio(String nombre, String descripcion, Integer duracionMinutos, Double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public Double getPrecio() {
        return precio;
    }

    public boolean isActivo() {
        return activo;
    }
}