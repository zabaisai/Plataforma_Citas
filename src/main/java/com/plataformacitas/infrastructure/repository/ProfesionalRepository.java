package com.plataformacitas.infrastructure.repository;

import com.plataformacitas.domain.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    Optional<Profesional> findByUsuarioId(Long usuarioId);
}