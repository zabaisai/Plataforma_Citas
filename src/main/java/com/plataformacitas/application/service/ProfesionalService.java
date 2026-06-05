package com.plataformacitas.application.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.exception.DatosInvalidosException;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.UsuarioRepository;

@Service
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProfesionalService(
            ProfesionalRepository profesionalRepository,
            UsuarioRepository usuarioRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.profesionalRepository = profesionalRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Profesional> listarTodos() {
        return profesionalRepository.findAll();
    }

    public Profesional crearProfesional(
            String nombre,
            String email,
            String password,
            String especialidad,
            String descripcion
    ) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new DatosInvalidosException("Ya existe un usuario con ese correo.");
        }

        Usuario usuario = new Usuario(
                nombre,
                email,
                passwordEncoder.encode(password),
                RolUsuario.PROFESIONAL
        );

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Profesional profesional = new Profesional(
                usuarioGuardado,
                especialidad,
                descripcion
        );

        return profesionalRepository.save(profesional);
    }
}