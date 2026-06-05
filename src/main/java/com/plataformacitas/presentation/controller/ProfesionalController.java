package com.plataformacitas.presentation.controller;

import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.application.service.HorarioDisponibleService;
import com.plataformacitas.application.service.RecordatorioService;
import com.plataformacitas.domain.enums.DiaSemana;
import com.plataformacitas.domain.exception.DominioException;
import com.plataformacitas.domain.exception.UsuarioNoAutorizadoException;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

    private final ProfesionalRepository profesionalRepository;
    private final CitaService citaService;
    private final HorarioDisponibleService horarioDisponibleService;
    private final RecordatorioService recordatorioService;

    public ProfesionalController(
            ProfesionalRepository profesionalRepository,
            CitaService citaService,
            HorarioDisponibleService horarioDisponibleService,
            RecordatorioService recordatorioService
    ) {
        this.profesionalRepository = profesionalRepository;
        this.citaService = citaService;
        this.horarioDisponibleService = horarioDisponibleService;
        this.recordatorioService = recordatorioService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = obtenerUsuarioSesion(session);

        var profesional = profesionalRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new UsuarioNoAutorizadoException("No existe un profesional asociado a este usuario."));

        model.addAttribute("usuario", usuario);
        model.addAttribute("citas", citaService.listarPorProfesional(profesional.getId()));
        model.addAttribute("citasProximas", recordatorioService.citasProximasProfesional(profesional.getId()));

        return "dashboard-profesional";
    }

    @GetMapping("/horarios")
    public String horarios(HttpSession session, Model model) {
        Usuario usuario = obtenerUsuarioSesion(session);

        var profesional = profesionalRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new UsuarioNoAutorizadoException("No existe un profesional asociado a este usuario."));

        model.addAttribute("usuario", usuario);
        model.addAttribute("horarios", horarioDisponibleService.listarPorProfesional(profesional.getId()));

        return "horarios";
    }

    @PostMapping("/horarios")
    public String crearHorario(
            @RequestParam String diaSemana,
            @RequestParam String horaInicio,
            @RequestParam String horaFin,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Usuario usuario = obtenerUsuarioSesion(session);

            var profesional = profesionalRepository.findByUsuarioId(usuario.getId())
                    .orElseThrow(() -> new UsuarioNoAutorizadoException("No existe un profesional asociado a este usuario."));

            horarioDisponibleService.crearHorario(
                    profesional.getId(),
                    DiaSemana.valueOf(diaSemana),
                    LocalTime.parse(horaInicio),
                    LocalTime.parse(horaFin)
            );

            redirectAttributes.addFlashAttribute("success", "Horario registrado correctamente.");

        } catch (DominioException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/profesional/horarios";
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

    private Usuario obtenerUsuarioSesion(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            throw new UsuarioNoAutorizadoException("Debes iniciar sesión para acceder a esta sección.");
        }

        return usuario;
    }
}