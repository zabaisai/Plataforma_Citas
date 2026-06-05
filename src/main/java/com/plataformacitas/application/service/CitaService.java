package com.plataformacitas.application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataformacitas.domain.exception.CitaNoDisponibleException;
import com.plataformacitas.domain.exception.RecursoNoEncontradoException;
import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.domain.model.Cliente;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.infrastructure.repository.CitaRepository;
import com.plataformacitas.infrastructure.repository.ClienteRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;
    private final HorarioDisponibleService horarioDisponibleService;

    public CitaService(
            CitaRepository citaRepository,
            ClienteRepository clienteRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository,
            HorarioDisponibleService horarioDisponibleService
    ) {
        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
        this.horarioDisponibleService = horarioDisponibleService;
    }

    public Cita buscarPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada."));
    }

    @Transactional
    public Cita agendarCita(
            Long clienteId,
            Long profesionalId,
            Long servicioId,
            LocalDate fecha,
            LocalTime hora,
            String observaciones
    ) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado."));

        Profesional profesional = profesionalRepository.findById(profesionalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesional no encontrado."));

        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado."));

        horarioDisponibleService.validarDisponibilidadProfesional(
                profesional.getId(),
                fecha,
                hora
        );

        boolean ocupado = citaRepository.existsByProfesionalAndFechaAndHora(
                profesional,
                fecha,
                hora
        );

        if (ocupado) {
            throw new CitaNoDisponibleException("El profesional ya tiene una cita en ese horario.");
        }

        try {
            Cita cita = new Cita(
                    cliente,
                    profesional,
                    servicio,
                    fecha,
                    hora,
                    observaciones
            );

            return citaRepository.save(cita);

        } catch (DataIntegrityViolationException e) {
            throw new CitaNoDisponibleException(
                    "Ese horario acaba de ser reservado por otro usuario.",
                    e
            );
        }
    }

    @Transactional
    public Cita reprogramarCita(
            Long citaId,
            LocalDate nuevaFecha,
            LocalTime nuevaHora
    ) {
        Cita cita = buscarPorId(citaId);

        horarioDisponibleService.validarDisponibilidadProfesional(
                cita.getProfesional().getId(),
                nuevaFecha,
                nuevaHora
        );

        boolean ocupado = citaRepository.existsByProfesionalAndFechaAndHoraAndIdNot(
                cita.getProfesional(),
                nuevaFecha,
                nuevaHora,
                cita.getId()
        );

        if (ocupado) {
            throw new CitaNoDisponibleException(
                    "No se puede reprogramar. El horario ya está ocupado."
            );
        }

        cita.reprogramar(nuevaFecha, nuevaHora);

        return citaRepository.save(cita);
    }

    public List<Cita> listarPorCliente(Long clienteId) {
        return citaRepository.findByClienteId(clienteId);
    }

    public List<Cita> listarPorProfesional(Long profesionalId) {
        return citaRepository.findByProfesionalId(profesionalId);
    }

    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    @Transactional
    public Cita confirmar(Long id) {
        Cita cita = buscarPorId(id);

        cita.confirmar();

        return citaRepository.save(cita);
    }

    @Transactional
    public Cita cancelar(Long id) {
        Cita cita = buscarPorId(id);

        cita.cancelar();

        return citaRepository.save(cita);
    }

    @Transactional
    public Cita completar(Long id) {
        Cita cita = buscarPorId(id);

        cita.completar();

        return citaRepository.save(cita);
    }
}