package com.plataformacitas.application.service;

import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.exception.DatosInvalidosException;
import com.plataformacitas.domain.model.Cliente;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.ClienteRepository;
import com.plataformacitas.infrastructure.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    public Usuario registrarCliente(String nombre, String email, String password, String telefono) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new DatosInvalidosException("Ya existe un usuario registrado con ese correo.");
        }

        String passwordHash = passwordEncoder.encode(password);

        Usuario usuario = new Usuario(nombre, email, passwordHash, RolUsuario.CLIENTE);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente(usuarioGuardado, telefono);
        clienteRepository.save(cliente);

        return usuarioGuardado;
    }

    public Usuario login(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new DatosInvalidosException("Correo o contraseña incorrectos."));

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new DatosInvalidosException("Correo o contraseña incorrectos.");
        }

        if (!usuario.isActivo()) {
            throw new DatosInvalidosException("El usuario se encuentra desactivado.");
        }

        return usuario;
    }
}