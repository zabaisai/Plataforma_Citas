package com.plataformacitas.application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.plataformacitas.domain.enums.DiaSemana;
import com.plataformacitas.domain.exception.DatosInvalidosException;
import com.plataformacitas.domain.exception.HorarioNoDisponibleException;
import com.plataformacitas.domain.exception.RecursoNoEncontradoException;
import com.plataformacitas.domain.model.HorarioDisponible;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.infrastructure.repository.HorarioDisponibleRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;

@Service
public class HorarioDisponibleService {

    private final HorarioDisponibleRepository horarioDisponibleRepository;
    private final ProfesionalRepository profesionalRepository;

    public HorarioDisponibleService(
            HorarioDisponibleRepository horarioDisponibleRepository,
            ProfesionalRepository profesionalRepository
    ) {
        this.horarioDisponibleRepository = horarioDisponibleRepository;
        this.profesionalRepository = profesionalRepository;
    }

    public HorarioDisponible crearHorario(
            Long profesionalId,
            DiaSemana diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin
    ) {
        validarDatosHorario(diaSemana, horaInicio, horaFin);

        Profesional profesional = profesionalRepository.findById(profesionalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesional no encontrado."));

        HorarioDisponible horario = new HorarioDisponible(
                profesional,
                diaSemana,
                horaInicio,
                horaFin
        );

        return horarioDisponibleRepository.save(horario);
    }

    private void validarDatosHorario(
            DiaSemana diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin
    ) {
        if (diaSemana == null) {
            throw new DatosInvalidosException("Debes seleccionar un día de la semana.");
        }

        if (horaInicio == null || horaFin == null) {
            throw new DatosInvalidosException("Debes seleccionar hora de inicio y hora de fin.");
        }

        if (!horaFin.isAfter(horaInicio)) {
            throw new DatosInvalidosException("La hora de fin debe ser posterior a la hora de inicio.");
        }
    }

    public List<HorarioDisponible> listarTodos() {
        return horarioDisponibleRepository.findAll();
    }

    public List<HorarioDisponible> listarPorProfesional(Long profesionalId) {
        return horarioDisponibleRepository.findByProfesionalIdAndActivoTrue(profesionalId);
    }

    public void validarDisponibilidadProfesional(
            Long profesionalId,
            LocalDate fecha,
            LocalTime hora
    ) {
        DiaSemana diaSemana = DiaSemana.desdeFecha(fecha);

        boolean disponible = horarioDisponibleRepository.existeHorarioDisponible(
                profesionalId,
                diaSemana,
                hora
        );

        if (!disponible) {
            throw new HorarioNoDisponibleException(
                    "El profesional no tiene horario disponible para ese día y hora."
            );
        }
    }

    public List<LocalTime> generarHorasDisponibles(Long profesionalId, LocalDate fecha) {
        DiaSemana diaSemana = DiaSemana.desdeFecha(fecha);

        List<HorarioDisponible> horarios = horarioDisponibleRepository
                .findByProfesionalIdAndDiaSemanaAndActivoTrue(
                        profesionalId,
                        diaSemana
                );

        List<LocalTime> horasDisponibles = new ArrayList<>();

        for (HorarioDisponible horario : horarios) {
            LocalTime horaActual = horario.getHoraInicio();

            while (horaActual.isBefore(horario.getHoraFin())) {
                horasDisponibles.add(horaActual);
                horaActual = horaActual.plusMinutes(30);
            }
        }

        return horasDisponibles;
    }
}