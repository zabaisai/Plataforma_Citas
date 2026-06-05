package com.plataformacitas.presentation.controller;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.application.service.HorarioDisponibleService;
import com.plataformacitas.application.service.ServicioService;
import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.infrastructure.repository.CitaRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ServicioRepository servicioRepository;
    private final ProfesionalRepository profesionalRepository;
    private final CitaRepository citaRepository;
    private final CitaService citaService;
    private final HorarioDisponibleService horarioDisponibleService;
    private final ServicioService servicioService;

    public ApiController(
            ServicioRepository servicioRepository,
            ProfesionalRepository profesionalRepository,
            CitaRepository citaRepository,
            CitaService citaService,
            HorarioDisponibleService horarioDisponibleService,
            ServicioService servicioService
    ) {
        this.servicioRepository = servicioRepository;
        this.profesionalRepository = profesionalRepository;
        this.citaRepository = citaRepository;
        this.citaService = citaService;
        this.horarioDisponibleService = horarioDisponibleService;
        this.servicioService = servicioService;
    }

    @GetMapping("/servicios")
    public List<Servicio> listarServicios() {
        return servicioRepository.findByActivoTrue();
    }

    @GetMapping("/profesionales")
    public List<Profesional> listarProfesionales() {
        return profesionalRepository.findAll();
    }

    @GetMapping("/servicios/{id}/profesionales")
    public List<Profesional> listarProfesionalesPorServicio(@PathVariable Long id) {
        Servicio servicio = servicioService.buscarPorId(id);

        return profesionalRepository.findByEspecialidadIgnoreCase(
                servicio.getEspecialidadRequerida()
        );
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