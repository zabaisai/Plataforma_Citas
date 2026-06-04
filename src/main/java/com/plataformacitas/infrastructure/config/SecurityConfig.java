package com.plataformacitas.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuración de seguridad básica del sistema.
 *
 * En esta primera versión se usa BCrypt para encriptar contraseñas.
 * La autenticación se maneja desde AuthService usando HttpSession.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}