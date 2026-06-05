package com.plataformacitas.application.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plataformacitas.domain.enums.EstadoCita;
import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.infrastructure.repository.CitaRepository;

@Service
public class RecordatorioService {

    private final CitaRepository citaRepository;

    public RecordatorioService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Optional<Cita> proximaCitaCliente(Long clienteId) {
        return citaRepository.findByClienteId(clienteId)
                .stream()
                .filter(cita -> cita.getEstado() != EstadoCita.CANCELADA)
                .filter(cita -> cita.getEstado() != EstadoCita.COMPLETADA)
                .filter(cita -> !cita.getFecha().isBefore(LocalDate.now()))
                .min(Comparator.comparing(Cita::getFecha).thenComparing(Cita::getHora));
    }

    public List<Cita> citasProximasProfesional(Long profesionalId) {
        return citaRepository.findByProfesionalId(profesionalId)
                .stream()
                .filter(cita -> cita.getEstado() != EstadoCita.CANCELADA)
                .filter(cita -> cita.getEstado() != EstadoCita.COMPLETADA)
                .filter(cita -> !cita.getFecha().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Cita::getFecha).thenComparing(Cita::getHora))
                .limit(5)
                .toList();
    }
}