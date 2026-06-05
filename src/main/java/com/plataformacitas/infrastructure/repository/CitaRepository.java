package com.plataformacitas.infrastructure.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.domain.model.Profesional;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByProfesionalAndFechaAndHora(
            Profesional profesional,
            LocalDate fecha,
            LocalTime hora
    );

    boolean existsByProfesionalAndFechaAndHoraAndIdNot(
            Profesional profesional,
            LocalDate fecha,
            LocalTime hora,
            Long id
    );

    List<Cita> findByClienteId(Long clienteId);

    List<Cita> findByProfesionalId(Long profesionalId);

    List<Cita> findByProfesionalIdAndFecha(Long profesionalId, LocalDate fecha);
}