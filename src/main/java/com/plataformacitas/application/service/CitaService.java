package com.plataformacitas.application.service;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;

    public CitaService(
            CitaRepository citaRepository,
            ClienteRepository clienteRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository
    ) {
        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
    }

    @Transactional
    public Cita agendarCita(Long clienteId, Long profesionalId, Long servicioId, LocalDate fecha, LocalTime hora, String observaciones) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado."));

        Profesional profesional = profesionalRepository.findById(profesionalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesional no encontrado."));

        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado."));

        boolean ocupado = citaRepository.existsByProfesionalAndFechaAndHora(profesional, fecha, hora);

        if (ocupado) {
            throw new CitaNoDisponibleException("El profesional ya tiene una cita en ese horario.");
        }

        try {
            Cita cita = new Cita(cliente, profesional, servicio, fecha, hora, observaciones);
            return citaRepository.save(cita);
        } catch (DataIntegrityViolationException e) {
            throw new CitaNoDisponibleException("Ese horario acaba de ser reservado por otro usuario.");
        }
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
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada."));

        cita.confirmar();
        return citaRepository.save(cita);
    }

    @Transactional
    public Cita cancelar(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada."));

        cita.cancelar();
        return citaRepository.save(cita);
    }

    @Transactional
    public Cita completar(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada."));

        cita.completar();
        return citaRepository.save(cita);
    }
}