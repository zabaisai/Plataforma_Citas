package com.plataformacitas.presentation.controller;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

    private final ProfesionalRepository profesionalRepository;
    private final CitaService citaService;

    public ProfesionalController(ProfesionalRepository profesionalRepository, CitaService citaService) {
        this.profesionalRepository = profesionalRepository;
        this.citaService = citaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        var profesional = profesionalRepository.findByUsuarioId(usuario.getId()).orElseThrow();

        model.addAttribute("usuario", usuario);
        model.addAttribute("citas", citaService.listarPorProfesional(profesional.getId()));

        return "dashboard-profesional";
    }

    @PostMapping("/citas/{id}/confirmar")
    public String confirmar(@PathVariable Long id) {
        citaService.confirmar(id);
        return "redirect:/profesional/dashboard";
    }

    @PostMapping("/citas/{id}/completar")
    public String completar(@PathVariable Long id) {
        citaService.completar(id);
        return "redirect:/profesional/dashboard";
    }

    @PostMapping("/citas/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        citaService.cancelar(id);
        return "redirect:/profesional/dashboard";
    }
}