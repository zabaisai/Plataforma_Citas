package com.plataformacitas.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;

@Service
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;

    public ProfesionalService(ProfesionalRepository profesionalRepository) {
        this.profesionalRepository = profesionalRepository;
    }

    public List<Profesional> listarTodos() {
        return profesionalRepository.findAll();
    }
}