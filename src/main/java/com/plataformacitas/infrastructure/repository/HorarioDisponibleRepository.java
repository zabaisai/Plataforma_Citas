package com.plataformacitas.infrastructure.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.plataformacitas.domain.enums.DiaSemana;
import com.plataformacitas.domain.model.HorarioDisponible;

public interface HorarioDisponibleRepository extends JpaRepository<HorarioDisponible, Long> {

    List<HorarioDisponible> findByProfesionalIdAndActivoTrue(Long profesionalId);

    List<HorarioDisponible> findByProfesionalIdAndDiaSemanaAndActivoTrue(
            Long profesionalId,
            DiaSemana diaSemana
    );

    @Query("""
           SELECT COUNT(h) > 0
           FROM HorarioDisponible h
           WHERE h.profesional.id = :profesionalId
           AND h.diaSemana = :diaSemana
           AND h.activo = true
           AND h.horaInicio <= :hora
           AND h.horaFin > :hora
           """)
    boolean existeHorarioDisponible(
            Long profesionalId,
            DiaSemana diaSemana,
            LocalTime hora
    );
}