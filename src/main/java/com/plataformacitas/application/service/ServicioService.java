package com.plataformacitas.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.infrastructure.repository.ServicioRepository;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> listarActivos() {
        return servicioRepository.findByActivoTrue();
    }

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
}