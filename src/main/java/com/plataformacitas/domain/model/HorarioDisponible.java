package com.plataformacitas.domain.model;

import java.time.LocalTime;

import com.plataformacitas.domain.enums.DiaSemana;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "horarios_disponibles")
public class HorarioDisponible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Profesional profesional;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    private boolean activo = true;

    public HorarioDisponible() {
    }

    public HorarioDisponible(Profesional profesional, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        if (profesional == null) {
            throw new IllegalArgumentException("El horario debe estar asociado a un profesional.");
        }

        if (diaSemana == null) {
            throw new IllegalArgumentException("Debe seleccionar un día de la semana.");
        }

        if (horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("Debe definir hora de inicio y hora de fin.");
        }

        if (!horaFin.isAfter(horaInicio)) {
            throw new IllegalArgumentException("La hora final debe ser posterior a la hora inicial.");
        }

        this.profesional = profesional;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public void desactivar() {
        this.activo = false;
    }

    public Long getId() {
        return id;
    }

    public Profesional getProfesional() {
        return profesional;
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