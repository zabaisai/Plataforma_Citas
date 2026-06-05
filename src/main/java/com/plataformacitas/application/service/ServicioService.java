package com.plataformacitas.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataformacitas.domain.exception.DatosInvalidosException;
import com.plataformacitas.domain.exception.RecursoNoEncontradoException;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.infrastructure.repository.ServicioRepository;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public List<Servicio> listarActivos() {
        return servicioRepository.findByActivoTrue();
    }

    public Servicio buscarPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado."));
    }

    public Servicio crearServicio(
            String nombre,
            String descripcion,
            Integer duracionMinutos,
            Double precio
    ) {
        validarPrecio(precio);

        Servicio servicio = new Servicio(
                nombre,
                descripcion,
                duracionMinutos,
                precio
        );

        return servicioRepository.save(servicio);
    }

    @Transactional
    public Servicio actualizarPrecio(Long servicioId, Double nuevoPrecio) {
        validarPrecio(nuevoPrecio);

        Servicio servicio = buscarPorId(servicioId);
        servicio.actualizarPrecio(nuevoPrecio);

        return servicioRepository.save(servicio);
    }

    private void validarPrecio(Double precio) {
        if (precio == null) {
            throw new DatosInvalidosException("El precio no puede estar vacío.");
        }

        if (precio <= 0) {
            throw new DatosInvalidosException("El precio debe ser mayor a cero.");
        }
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
}