package com.plataformacitas.application.dto;

import com.plataformacitas.domain.model.Servicio;

public class ServicioDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer duracionMinutos;
    private Double precio;
    private boolean activo;

    public ServicioDTO() {
    }

    public ServicioDTO(
            Long id,
            String nombre,
            String descripcion,
            Integer duracionMinutos,
            Double precio,
            boolean activo
    ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.activo = activo;
    }

    public static ServicioDTO desdeEntidad(Servicio servicio) {
        return new ServicioDTO(
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getDuracionMinutos(),
                servicio.getPrecio(),
                servicio.isActivo()
        );
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