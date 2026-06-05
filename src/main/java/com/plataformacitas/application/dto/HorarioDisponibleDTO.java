package com.plataformacitas.application.dto;

import java.time.LocalTime;

import com.plataformacitas.domain.enums.DiaSemana;
import com.plataformacitas.domain.model.HorarioDisponible;

public class HorarioDisponibleDTO {

    private Long id;

    private Long profesionalId;
    private String nombreProfesional;
    private String especialidadProfesional;

    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private boolean activo;

    public HorarioDisponibleDTO() {
    }

    public HorarioDisponibleDTO(
            Long id,
            Long profesionalId,
            String nombreProfesional,
            String especialidadProfesional,
            DiaSemana diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin,
            boolean activo
    ) {
        this.id = id;
        this.profesionalId = profesionalId;
        this.nombreProfesional = nombreProfesional;
        this.especialidadProfesional = especialidadProfesional;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.activo = activo;
    }

    public static HorarioDisponibleDTO desdeEntidad(HorarioDisponible horario) {
        return new HorarioDisponibleDTO(
                horario.getId(),
                horario.getProfesional().getId(),
                horario.getProfesional().getUsuario().getNombre(),
                horario.getProfesional().getEspecialidad(),
                horario.getDiaSemana(),
                horario.getHoraInicio(),
                horario.getHoraFin(),
                horario.isActivo()
        );
    }

    public Long getId() {
        return id;
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

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public boolean isActivo() {
        return activo;
    }
}