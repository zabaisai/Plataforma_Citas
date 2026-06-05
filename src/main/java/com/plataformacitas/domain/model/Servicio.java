package com.plataformacitas.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
        if (precio == null || precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
    }

    public void actualizarPrecio(Double nuevoPrecio) {
        if (nuevoPrecio == null || nuevoPrecio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }

        this.precio = nuevoPrecio;
    }

    public void desactivar() {
        this.activo = false;
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