package com.plataformacitas.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.plataformacitas.domain.enums.EstadoCita;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "citas",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"profesional_id", "fecha", "hora"})
        }
)
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    private Profesional profesional;

    @ManyToOne(optional = false)
    private Servicio servicio;

    private LocalDate fecha;

    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PENDIENTE;

    private String observaciones;

    public Cita() {
    }

    public Cita(
            Cliente cliente,
            Profesional profesional,
            Servicio servicio,
            LocalDate fecha,
            LocalTime hora,
            String observaciones
    ) {
        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se pueden crear citas en fechas pasadas.");
        }

        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.observaciones = observaciones;
    }

    public void confirmar() {
        if (estado != EstadoCita.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden confirmar citas pendientes.");
        }

        estado = EstadoCita.CONFIRMADA;
    }

    public void cancelar() {
        if (estado == EstadoCita.COMPLETADA) {
            throw new IllegalStateException("No se puede cancelar una cita completada.");
        }

        estado = EstadoCita.CANCELADA;
    }

    public void completar() {
        if (estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se pueden completar citas confirmadas.");
        }

        estado = EstadoCita.COMPLETADA;
    }

    public void reprogramar(LocalDate nuevaFecha, LocalTime nuevaHora) {
        if (estado == EstadoCita.CANCELADA || estado == EstadoCita.COMPLETADA) {
            throw new IllegalStateException("No se puede reprogramar una cita cancelada o completada.");
        }

        if (nuevaFecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede reprogramar una cita a una fecha pasada.");
        }

        this.fecha = nuevaFecha;
        this.hora = nuevaHora;
        this.estado = EstadoCita.PENDIENTE;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public Servicio getServicio() {
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

    public String getObservaciones() {
        return observaciones;
    }
}