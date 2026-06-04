package com.plataformacitas.infrastructure.repository;

import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.domain.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByProfesionalAndFechaAndHora(
            Profesional profesional,
            LocalDate fecha,
            LocalTime hora
    );

    List<Cita> findByClienteId(Long clienteId);

    List<Cita> findByProfesionalId(Long profesionalId);

    List<Cita> findByProfesionalIdAndFecha(Long profesionalId, LocalDate fecha);
}