package com.plataformacitas.infrastructure.config;

import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;
import com.plataformacitas.infrastructure.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
    }

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!usuarioRepository.existsByEmail("admin@citas.com")) {
            Usuario admin = new Usuario(
                    "Administrador",
                    "admin@citas.com",
                    encoder.encode("123456"),
                    RolUsuario.ADMIN
            );
            usuarioRepository.save(admin);
        }

        if (!usuarioRepository.existsByEmail("profesional@citas.com")) {
            Usuario usuarioProfesional = new Usuario(
                    "Laura Gómez",
                    "profesional@citas.com",
                    encoder.encode("123456"),
                    RolUsuario.PROFESIONAL
            );

            Usuario profesionalGuardado = usuarioRepository.save(usuarioProfesional);

            Profesional profesional = new Profesional(
                    profesionalGuardado,
                    "Psicología",
                    "Profesional especializada en atención personalizada."
            );

            profesionalRepository.save(profesional);
        }

        if (servicioRepository.count() == 0) {
            servicioRepository.save(new Servicio(
                    "Consulta general",
                    "Atención básica para valoración inicial.",
                    30,
                    50000.0
            ));

            servicioRepository.save(new Servicio(
                    "Consulta especializada",
                    "Atención personalizada con profesional especializado.",
                    60,
                    90000.0
            ));
        }
    }
}