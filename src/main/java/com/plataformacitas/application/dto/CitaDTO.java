package com.plataformacitas.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.plataformacitas.domain.enums.EstadoCita;
import com.plataformacitas.domain.model.Cita;

public class CitaDTO {

    private Long id;

    private Long clienteId;
    private String nombreCliente;

    private Long profesionalId;
    private String nombreProfesional;
    private String especialidadProfesional;

    private Long servicioId;
    private String nombreServicio;

    private LocalDate fecha;
    private LocalTime hora;

    private EstadoCita estado;
    private String observaciones;

    public CitaDTO() {
    }

    public CitaDTO(
            Long id,
            Long clienteId,
            String nombreCliente,
            Long profesionalId,
            String nombreProfesional,
            String especialidadProfesional,
            Long servicioId,
            String nombreServicio,
            LocalDate fecha,
            LocalTime hora,
            EstadoCita estado,
            String observaciones
    ) {
        this.id = id;
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.profesionalId = profesionalId;
        this.nombreProfesional = nombreProfesional;
        this.especialidadProfesional = especialidadProfesional;
        this.servicioId = servicioId;
        this.nombreServicio = nombreServicio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public static CitaDTO desdeEntidad(Cita cita) {
        return new CitaDTO(
                cita.getId(),
                cita.getCliente().getId(),
                cita.getCliente().getUsuario().getNombre(),
                cita.getProfesional().getId(),
                cita.getProfesional().getUsuario().getNombre(),
                cita.getProfesional().getEspecialidad(),
                cita.getServicio().getId(),
                cita.getServicio().getNombre(),
                cita.getFecha(),
                cita.getHora(),
                cita.getEstado(),
                cita.getObservaciones()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public Long getProfesionalId() {
        return profesionalId;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public String getEspecialidadProfesional() {
        return especialidadProfesional;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public String getNombreServicio() {
        return nombreServicio;
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

    public String getObservaciones() {
        return observaciones;
    }
}