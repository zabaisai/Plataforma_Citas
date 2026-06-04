package com.plataformacitas;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.plataformacitas.domain.enums.EstadoCita;
import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.domain.model.Cliente;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.CitaRepository;
import com.plataformacitas.infrastructure.repository.ClienteRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;
import com.plataformacitas.infrastructure.repository.UsuarioRepository;

@SpringBootTest
@Transactional
class CitaRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Test
    void debeGuardarYConsultarCitaPorCliente() {
        Usuario usuarioCliente = usuarioRepository.save(
                new Usuario("Cliente Test", "cliente.repository@test.com", "123456", RolUsuario.CLIENTE)
        );

        Usuario usuarioProfesional = usuarioRepository.save(
                new Usuario("Profesional Test", "prof.repository@test.com", "123456", RolUsuario.PROFESIONAL)
        );

        Cliente cliente = clienteRepository.save(
                new Cliente(usuarioCliente, "3001234567")
        );

        Profesional profesional = profesionalRepository.save(
                new Profesional(usuarioProfesional, "Psicología", "Profesional de prueba")
        );

        Servicio servicio = servicioRepository.save(
                new Servicio("Consulta general", "Consulta inicial", 30, 50000.0)
        );

        Cita cita = new Cita(
                cliente,
                profesional,
                servicio,
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                "Cita de prueba"
        );

        Cita citaGuardada = citaRepository.save(cita);

        assertNotNull(citaGuardada.getId());
        assertEquals(EstadoCita.PENDIENTE, citaGuardada.getEstado());
        assertEquals(1, citaRepository.findByClienteId(cliente.getId()).size());
    }

    @Test
    void debeDetectarHorarioOcupadoParaProfesional() {
        Usuario usuarioCliente = usuarioRepository.save(
                new Usuario("Cliente Horario", "cliente.horario@test.com", "123456", RolUsuario.CLIENTE)
        );

        Usuario usuarioProfesional = usuarioRepository.save(
                new Usuario("Profesional Horario", "prof.horario@test.com", "123456", RolUsuario.PROFESIONAL)
        );

        Cliente cliente = clienteRepository.save(
                new Cliente(usuarioCliente, "3010000000")
        );

        Profesional profesional = profesionalRepository.save(
                new Profesional(usuarioProfesional, "Medicina", "Profesional de prueba")
        );

        Servicio servicio = servicioRepository.save(
                new Servicio("Consulta médica", "Consulta básica", 30, 60000.0)
        );

        LocalDate fecha = LocalDate.now().plusDays(2);
        LocalTime hora = LocalTime.of(14, 0);

        citaRepository.save(
                new Cita(cliente, profesional, servicio, fecha, hora, "Primera cita")
        );

        boolean ocupado = citaRepository.existsByProfesionalAndFechaAndHora(
                profesional,
                fecha,
                hora
        );

        assertTrue(ocupado);
    }
}