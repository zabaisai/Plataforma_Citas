package com.plataformacitas.presentation.controller;

import com.plataformacitas.application.service.AuthService;
import com.plataformacitas.domain.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginVista() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        try {
            Usuario usuario = authService.login(email, password);
            session.setAttribute("usuario", usuario);

            return switch (usuario.getRol()) {
                case CLIENTE -> "redirect:/cliente/dashboard";
                case PROFESIONAL -> "redirect:/profesional/dashboard";
                case ADMIN -> "redirect:/admin/dashboard";
            };

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/registro")
    public String registroVista() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String telefono,
            Model model
    ) {
        try {
            authService.registrarCliente(nombre, email, password, telefono);
            model.addAttribute("success", "Usuario registrado correctamente. Ahora puedes iniciar sesión.");
            return "login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}