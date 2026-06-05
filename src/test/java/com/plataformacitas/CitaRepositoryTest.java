package com.plataformacitas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:sqlite:file:testdb?mode=memory&cache=shared",
        "spring.datasource.driver-class-name=org.sqlite.JDBC",
        "spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CitaRepositoryTest {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Test
    @DisplayName("Debe guardar y consultar una cita por cliente")
    void debeGuardarYConsultarCitaPorCliente() {
        Cliente cliente = crearCliente();
        Profesional profesional = crearProfesional();
        Servicio servicio = crearServicio();

        Cita cita = new Cita(
                cliente,
                profesional,
                servicio,
                LocalDate.of(2026, 6, 5),
                LocalTime.of(10, 0),
                "Primera consulta"
        );

        citaRepository.save(cita);

        List<Cita> citasCliente = citaRepository.findByClienteId(cliente.getId());

        assertFalse(citasCliente.isEmpty());
        assertEquals(1, citasCliente.size());
        assertEquals(EstadoCita.PENDIENTE, citasCliente.get(0).getEstado());
        assertEquals(cliente.getId(), citasCliente.get(0).getCliente().getId());
    }

    @Test
    @DisplayName("Debe detectar si un profesional ya tiene cita en una fecha y hora")
    void debeDetectarHorarioOcupadoPorProfesional() {
        Cliente cliente = crearCliente();
        Profesional profesional = crearProfesional();
        Servicio servicio = crearServicio();

        LocalDate fecha = LocalDate.of(2026, 6, 5);
        LocalTime hora = LocalTime.of(10, 0);

        Cita cita = new Cita(
                cliente,
                profesional,
                servicio,
                fecha,
                hora,
                "Primera consulta"
        );

        citaRepository.save(cita);

        boolean ocupado = citaRepository.existsByProfesionalAndFechaAndHora(
                profesional,
                fecha,
                hora
        );

        assertTrue(ocupado);
    }

    private Cliente crearCliente() {
        Usuario usuario = new Usuario(
                "Carlos Pérez",
                "carlos@test.com",
                "password",
                RolUsuario.CLIENTE
        );

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente(
                usuarioGuardado,
                "3001234567"
        );

        return clienteRepository.save(cliente);
    }

    private Profesional crearProfesional() {
        Usuario usuario = new Usuario(
                "Laura Gómez",
                "laura@test.com",
                "password",
                RolUsuario.PROFESIONAL
        );

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Profesional profesional = new Profesional(
                usuarioGuardado,
                "Psicología",
                "Psicóloga profesional."
        );

        return profesionalRepository.save(profesional);
    }

    private Servicio crearServicio() {
        Servicio servicio = new Servicio(
                "Consulta psicológica",
                "Consulta profesional de psicología.",
                60,
                90000.0,
                "Psicología"
        );

        return servicioRepository.save(servicio);
    }
}