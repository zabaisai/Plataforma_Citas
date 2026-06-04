package com.plataformacitas.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.plataformacitas.domain.enums.EstadoCita;

public class CitaDTO {

    private Long id;
    private String cliente;
    private String profesional;
    private String servicio;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoCita estado;

    public CitaDTO() {
    }

    public CitaDTO(Long id, String cliente, String profesional, String servicio, LocalDate fecha, LocalTime hora, EstadoCita estado) {
        this.id = id;
        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getProfesional() {
        return profesional;
    }

    public String getServicio() {
        return servicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public EstadoCita getEstado() {
        return estado;
    }
}