package com.plataformacitas.presentation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.application.service.HorarioDisponibleService;
import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.infrastructure.repository.CitaRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ServicioRepository servicioRepository;
    private final ProfesionalRepository profesionalRepository;
    private final CitaRepository citaRepository;
    private final CitaService citaService;
    private final HorarioDisponibleService horarioDisponibleService;

    public ApiController(
            ServicioRepository servicioRepository,
            ProfesionalRepository profesionalRepository,
            CitaRepository citaRepository,
            CitaService citaService,
            HorarioDisponibleService horarioDisponibleService
    ) {
        this.servicioRepository = servicioRepository;
        this.profesionalRepository = profesionalRepository;
        this.citaRepository = citaRepository;
        this.citaService = citaService;
        this.horarioDisponibleService = horarioDisponibleService;
    }

    @GetMapping("/servicios")
    public List<Servicio> listarServicios() {
        return servicioRepository.findByActivoTrue();
    }

    @GetMapping("/profesionales")
    public List<Profesional> listarProfesionales() {
        return profesionalRepository.findAll();
    }

    @GetMapping("/citas")
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @GetMapping("/profesionales/{id}/horarios-disponibles")
    public List<String> listarHorasDisponibles(
            @PathVariable Long id,
            @RequestParam String fecha
    ) {
        LocalDate fechaConsulta = LocalDate.parse(fecha);

        Profesional profesional = profesionalRepository.findById(id)
                .orElseThrow();

        List<LocalTime> horas = horarioDisponibleService.generarHorasDisponibles(
                id,
                fechaConsulta
        );

        return horas.stream()
                .filter(hora -> !citaRepository.existsByProfesionalAndFechaAndHora(
                        profesional,
                        fechaConsulta,
                        hora
                ))
                .map(LocalTime::toString)
                .toList();
    }

    @GetMapping("/profesionales/{id}/disponibilidad")
    public String consultarDisponibilidad(
            @PathVariable Long id,
            @RequestParam String fecha,
            @RequestParam String hora
    ) {
        Profesional profesional = profesionalRepository.findById(id)
                .orElseThrow();

        boolean ocupado = citaRepository.existsByProfesionalAndFechaAndHora(
                profesional,
                LocalDate.parse(fecha),
                LocalTime.parse(hora)
        );

        if (ocupado) {
            return "Horario no disponible";
        }

        return "Horario disponible";
    }

    @PostMapping("/citas")
    public Cita crearCita(@RequestBody CrearCitaRequest request) {
        return citaService.agendarCita(
                request.clienteId(),
                request.profesionalId(),
                request.servicioId(),
                request.fecha(),
                request.hora(),
                request.observaciones()
        );
    }

    @PatchMapping("/citas/{id}/confirmar")
    public Cita confirmarCita(@PathVariable Long id) {
        return citaService.confirmar(id);
    }

    @PatchMapping("/citas/{id}/cancelar")
    public Cita cancelarCita(@PathVariable Long id) {
        return citaService.cancelar(id);
    }

    @PatchMapping("/citas/{id}/completar")
    public Cita completarCita(@PathVariable Long id) {
        return citaService.completar(id);
    }

    public record CrearCitaRequest(
            Long clienteId,
            Long profesionalId,
            Long servicioId,
            LocalDate fecha,
            LocalTime hora,
            String observaciones
    ) {
    }
}