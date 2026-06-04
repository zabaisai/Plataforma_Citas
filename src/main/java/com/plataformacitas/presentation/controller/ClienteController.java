package com.plataformacitas.presentation.controller;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.ClienteRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;
    private final CitaService citaService;

    public ClienteController(
            ClienteRepository clienteRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository,
            CitaService citaService
    ) {
        this.clienteRepository = clienteRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
        this.citaService = citaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        var cliente = clienteRepository.findByUsuarioId(usuario.getId()).orElseThrow();

        model.addAttribute("usuario", usuario);
        model.addAttribute("servicios", servicioRepository.findByActivoTrue());
        model.addAttribute("profesionales", profesionalRepository.findAll());
        model.addAttribute("citas", citaService.listarPorCliente(cliente.getId()));

        return "dashboard-cliente";
    }

    @PostMapping("/citas")
    public String agendar(
            @RequestParam Long profesionalId,
            @RequestParam Long servicioId,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam(required = false) String observaciones,
            HttpSession session,
            Model model
    ) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        var cliente = clienteRepository.findByUsuarioId(usuario.getId()).orElseThrow();

        try {
            citaService.agendarCita(
                    cliente.getId(),
                    profesionalId,
                    servicioId,
                    LocalDate.parse(fecha),
                    LocalTime.parse(hora),
                    observaciones
            );
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/cliente/dashboard";
    }

    @PostMapping("/citas/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        citaService.cancelar(id);
        return "redirect:/cliente/dashboard";
    }
}