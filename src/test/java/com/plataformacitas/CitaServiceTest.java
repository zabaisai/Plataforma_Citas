package com.plataformacitas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.plataformacitas.application.service.CitaService;
import com.plataformacitas.application.service.HorarioDisponibleService;
import com.plataformacitas.domain.enums.EstadoCita;
import com.plataformacitas.domain.enums.RolUsuario;
import com.plataformacitas.domain.exception.CitaNoDisponibleException;
import com.plataformacitas.domain.model.Cita;
import com.plataformacitas.domain.model.Cliente;
import com.plataformacitas.domain.model.Profesional;
import com.plataformacitas.domain.model.Servicio;
import com.plataformacitas.domain.model.Usuario;
import com.plataformacitas.infrastructure.repository.CitaRepository;
import com.plataformacitas.infrastructure.repository.ClienteRepository;
import com.plataformacitas.infrastructure.repository.ProfesionalRepository;
import com.plataformacitas.infrastructure.repository.ServicioRepository;

class CitaServiceTest {

    private CitaRepository citaRepository;
    private ClienteRepository clienteRepository;
    private ProfesionalRepository profesionalRepository;
    private ServicioRepository servicioRepository;
    private HorarioDisponibleService horarioDisponibleService;
    private CitaService citaService;

    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;
    private Cita cita;

    @BeforeEach
    void setUp() {
        citaRepository = Mockito.mock(CitaRepository.class);
        clienteRepository = Mockito.mock(ClienteRepository.class);
        profesionalRepository = Mockito.mock(ProfesionalRepository.class);
        servicioRepository = Mockito.mock(ServicioRepository.class);
        horarioDisponibleService = Mockito.mock(HorarioDisponibleService.class);

        citaService = new CitaService(
                citaRepository,
                clienteRepository,
                profesionalRepository,
                servicioRepository,
                horarioDisponibleService
        );

        Usuario usuarioCliente = new Usuario(
                "Carlos Pérez",
                "carlos@email.com",
                "password-encriptado",
                RolUsuario.CLIENTE
        );

        Usuario usuarioProfesional = new Usuario(
                "Laura Gómez",
                "laura@email.com",
                "password-encriptado",
                RolUsuario.PROFESIONAL
        );

        cliente = new Cliente(
                usuarioCliente,
                "3001234567"
        );

        profesional = new Profesional(
                usuarioProfesional,
                "Psicología",
                "Psicóloga profesional."
        );

        servicio = new Servicio(
                "Consulta psicológica",
                "Consulta profesional de psicología.",
                60,
                90000.0,
                "Psicología"
        );

        cita = new Cita(
                cliente,
                profesional,
                servicio,
                LocalDate.of(2026, 6, 5),
                LocalTime.of(10, 0),
                "Primera cita"
        );
    }

    @Test
    void debeAgendarCitaCuandoHorarioEstaDisponible() {
        LocalDate fecha = LocalDate.of(2026, 6, 5);
        LocalTime hora = LocalTime.of(10, 0);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(profesionalRepository.findById(1L)).thenReturn(Optional.of(profesional));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        doNothing().when(horarioDisponibleService)
                .validarDisponibilidadProfesional(1L, fecha, hora);

        when(citaRepository.existsByProfesionalAndFechaAndHora(
                profesional,
                fecha,
                hora
        )).thenReturn(false);

        when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = citaService.agendarCita(
                1L,
                1L,
                1L,
                fecha,
                hora,
                "Primera cita"
        );

        assertNotNull(resultado);
        assertEquals(EstadoCita.PENDIENTE, resultado.getEstado());
        assertEquals(cliente, resultado.getCliente());
        assertEquals(profesional, resultado.getProfesional());
        assertEquals(servicio, resultado.getServicio());

        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void noDebeAgendarCitaSiHorarioEstaOcupado() {
        LocalDate fecha = LocalDate.of(2026, 6, 5);
        LocalTime hora = LocalTime.of(10, 0);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(profesionalRepository.findById(1L)).thenReturn(Optional.of(profesional));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        doNothing().when(horarioDisponibleService)
                .validarDisponibilidadProfesional(1L, fecha, hora);

        when(citaRepository.existsByProfesionalAndFechaAndHora(
                profesional,
                fecha,
                hora
        )).thenReturn(true);

        assertThrows(
                CitaNoDisponibleException.class,
                () -> citaService.agendarCita(
                        1L,
                        1L,
                        1L,
                        fecha,
                        hora,
                        "Primera cita"
                )
        );

        verify(citaRepository, never()).save(any(Cita.class));
    }

    @Test
    void debeConfirmarCitaPendiente() {
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = citaService.confirmar(1L);

        assertEquals(EstadoCita.CONFIRMADA, resultado.getEstado());
        verify(citaRepository, times(1)).save(cita);
    }

    @Test
    void debeCompletarCitaConfirmada() {
        cita.confirmar();

        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = citaService.completar(1L);

        assertEquals(EstadoCita.COMPLETADA, resultado.getEstado());
        verify(citaRepository, times(1)).save(cita);
    }

    @Test
    void debeCancelarCita() {
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = citaService.cancelar(1L);

        assertEquals(EstadoCita.CANCELADA, resultado.getEstado());
        verify(citaRepository, times(1)).save(cita);
    }

    @Test
    void debeReprogramarCitaCuandoNuevoHorarioEstaDisponible() {
        LocalDate nuevaFecha = LocalDate.of(2026, 6, 6);
        LocalTime nuevaHora = LocalTime.of(11, 0);

        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        doNothing().when(horarioDisponibleService)
                .validarDisponibilidadProfesional(
                        profesional.getId(),
                        nuevaFecha,
                        nuevaHora
                );

        when(citaRepository.existsByProfesionalAndFechaAndHoraAndIdNot(
                cita.getProfesional(),
                nuevaFecha,
                nuevaHora,
                cita.getId()
        )).thenReturn(false);

        when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = citaService.reprogramarCita(
                1L,
                nuevaFecha,
                nuevaHora
        );

        assertEquals(nuevaFecha, resultado.getFecha());
        assertEquals(nuevaHora, resultado.getHora());

        verify(citaRepository, times(1)).save(cita);
    }
}