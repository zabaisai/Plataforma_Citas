package com.plataformacitas.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.application.service.ProfesionalService;
import com.plataformacitas.application.service.ServicioService;
import com.plataformacitas.domain.exception.DominioException;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;
import com.plataformacitas.infrastructure.repository.UsuarioRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;
    private final CitaService citaService;
    private final ProfesionalService profesionalService;
    private final ServicioService servicioService;

    public AdminController(
            UsuarioRepository usuarioRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository,
            CitaService citaService,
            ProfesionalService profesionalService,
            ServicioService servicioService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
        this.citaService = citaService;
        this.profesionalService = profesionalService;
        this.servicioService = servicioService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("profesionales", profesionalRepository.findAll());
        model.addAttribute("servicios", servicioRepository.findAll());
        model.addAttribute("citas", citaService.listarTodas());

        return "dashboard-admin";
    }

    @PostMapping("/profesionales")
    public String crearProfesional(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String especialidad,
            @RequestParam String descripcion,
            RedirectAttributes redirectAttributes
    ) {
        try {
            profesionalService.crearProfesional(
                    nombre,
                    email,
                    password,
                    especialidad,
                    descripcion
            );

            redirectAttributes.addFlashAttribute("success", "Profesional creado correctamente.");

        } catch (DominioException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard#crear-profesional";
    }

    @PostMapping("/servicios")
    public String crearServicio(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam Integer duracionMinutos,
            @RequestParam Double precio,
            RedirectAttributes redirectAttributes
    ) {
        try {
            servicioService.crearServicio(
                    nombre,
                    descripcion,
                    duracionMinutos,
                    precio
            );

            redirectAttributes.addFlashAttribute("success", "Servicio creado correctamente.");

        } catch (DominioException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard#crear-servicio";
    }

    @PostMapping("/servicios/{id}/precio")
    public String actualizarPrecioServicio(
            @PathVariable Long id,
            @RequestParam Double precio,
            RedirectAttributes redirectAttributes
    ) {
        try {
            servicioService.actualizarPrecio(id, precio);

            redirectAttributes.addFlashAttribute("success", "Precio actualizado correctamente.");

        } catch (DominioException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/dashboard#servicios";
    }
}