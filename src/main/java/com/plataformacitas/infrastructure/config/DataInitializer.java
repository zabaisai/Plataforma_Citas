package com.plataformacitas.infrastructure.config;

import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.plataformacitas.domain.enums.DiaSemana;
import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.model.Cliente;
import com.plataformacitas.domain.model.HorarioDisponible;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.ClienteRepository;
import com.plataformacitas.infrastructure.repository.HorarioDisponibleRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;
import com.plataformacitas.infrastructure.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;
    private final HorarioDisponibleRepository horarioDisponibleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository,
            HorarioDisponibleRepository horarioDisponibleRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;
        this.horarioDisponibleRepository = horarioDisponibleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        crearUsuariosIniciales();
        crearServiciosIniciales();
        crearHorariosIniciales();
    }

    private void crearUsuariosIniciales() {
        if (!usuarioRepository.existsByEmail("admin@citas.com")) {
            Usuario admin = new Usuario(
                    "Administrador",
                    "admin@citas.com",
                    passwordEncoder.encode("123456"),
                    RolUsuario.ADMIN
            );

            usuarioRepository.save(admin);
        }

        if (!usuarioRepository.existsByEmail("profesional@citas.com")) {
            Usuario usuarioProfesional = new Usuario(
                    "Laura Gómez",
                    "profesional@citas.com",
                    passwordEncoder.encode("123456"),
                    RolUsuario.PROFESIONAL
            );

            Usuario profesionalGuardado = usuarioRepository.save(usuarioProfesional);

            Profesional profesional = new Profesional(
                    profesionalGuardado,
                    "Psicología",
                    "Psicóloga profesional con experiencia en acompañamiento emocional y orientación personal."
            );

            profesionalRepository.save(profesional);
        }

        if (!usuarioRepository.existsByEmail("cliente@citas.com")) {
            Usuario usuarioCliente = new Usuario(
                    "Cliente de prueba",
                    "cliente@citas.com",
                    passwordEncoder.encode("123456"),
                    RolUsuario.CLIENTE
            );

            Usuario clienteGuardado = usuarioRepository.save(usuarioCliente);

            Cliente cliente = new Cliente(
                    clienteGuardado,
                    "3001234567"
            );

            clienteRepository.save(cliente);
        }
    }

    private void crearServiciosIniciales() {
        if (servicioRepository.count() == 0) {
            Servicio consultaGeneral = new Servicio(
                    "Consulta médica general",
                    "Consulta médica general para revisión inicial, orientación básica y seguimiento preventivo.",
                    30,
                    50000.0,
                    "Medicina General"
            );

            Servicio consultaPsicologica = new Servicio(
                    "Consulta psicológica",
                    "Consulta profesional de psicología para orientación emocional, acompañamiento personal y bienestar mental.",
                    60,
                    90000.0,
                    "Psicología"
            );

            Servicio consultaOdontologica = new Servicio(
                    "Consulta odontológica",
                    "Consulta odontológica para valoración, diagnóstico inicial y recomendaciones de cuidado oral.",
                    45,
                    70000.0,
                    "Odontología"
            );

            Servicio consultaNutricion = new Servicio(
                    "Consulta de nutrición",
                    "Consulta nutricional para evaluación de hábitos alimenticios y orientación personalizada.",
                    45,
                    80000.0,
                    "Nutrición"
            );

            servicioRepository.save(consultaGeneral);
            servicioRepository.save(consultaPsicologica);
            servicioRepository.save(consultaOdontologica);
            servicioRepository.save(consultaNutricion);
        }
    }

    private void crearHorariosIniciales() {
        if (horarioDisponibleRepository.count() == 0) {
            profesionalRepository.findAll().forEach(profesional -> {
                HorarioDisponible lunes = new HorarioDisponible(
                        profesional,
                        DiaSemana.LUNES,
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0)
                );

                HorarioDisponible martes = new HorarioDisponible(
                        profesional,
                        DiaSemana.MARTES,
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0)
                );

                HorarioDisponible miercoles = new HorarioDisponible(
                        profesional,
                        DiaSemana.MIERCOLES,
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0)
                );

                HorarioDisponible jueves = new HorarioDisponible(
                        profesional,
                        DiaSemana.JUEVES,
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0)
                );

                HorarioDisponible viernes = new HorarioDisponible(
                        profesional,
                        DiaSemana.VIERNES,
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0)
                );

                horarioDisponibleRepository.save(lunes);
                horarioDisponibleRepository.save(martes);
                horarioDisponibleRepository.save(miercoles);
                horarioDisponibleRepository.save(jueves);
                horarioDisponibleRepository.save(viernes);
            });
        }
    }
}