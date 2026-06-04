package com.plataformacitas.presentation.controller;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;
import com.plataformacitas.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;
    private final CitaService citaService;

    public AdminController(
            UsuarioRepository usuarioRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository,
            CitaService citaService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
        this.citaService = citaService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("profesionales", profesionalRepository.findAll());
        model.addAttribute("servicios", servicioRepository.findAll());
        model.addAttribute("citas", citaService.listarTodas());

        return "dashboard-admin";
    }
}       