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

    private String especialidadRequerida;

    private boolean activo = true;

    public Servicio() {
    }

    public Servicio(
            String nombre,
            String descripcion,
            Integer duracionMinutos,
            Double precio,
            String especialidadRequerida
    ) {
        if (precio == null || precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }

        if (especialidadRequerida == null || especialidadRequerida.isBlank()) {
            throw new IllegalArgumentException("La especialidad requerida no puede estar vacía.");
        }

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.especialidadRequerida = especialidadRequerida;
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

    public String getEspecialidadRequerida() {
        return especialidadRequerida;
    }

    public boolean isActivo() {
        return activo;
    }
}