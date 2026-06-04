package com.plataformacitas.application.dto;

public class ServicioDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer duracionMinutos;
    private Double precio;

    public ServicioDTO() {
    }

    public ServicioDTO(Long id, String nombre, String descripcion, Integer duracionMinutos, Double precio) {
        this.id = id;
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
}