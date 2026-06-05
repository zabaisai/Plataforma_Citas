package com.plataformacitas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProfesionalRepository profesionalRepository;

    @Mock
    private ServicioRepository servicioRepository;

    private HorarioDisponibleService horarioDisponibleService;

    private CitaService citaService;

    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;

    @BeforeEach
    void setUp() {
        horarioDisponibleService = new HorarioDisponibleService(null, null) {
            @Override
            public void validarDisponibilidadProfesional(Long profesionalId, LocalDate fecha, LocalTime hora) {
                // Simulación para pruebas: se asume que el horario está disponible.
            }
        };

        citaService = new CitaService(
                citaRepository,
                clienteRepository,
                profesionalRepository,
                servicioRepository,
                horarioDisponibleService
        );

        Usuario usuarioCliente = new Usuario(
                "Cliente Test",
                "cliente.service@test.com",
                "123456",
                RolUsuario.CLIENTE
        );

        Usuario usuarioProfesional = new Usuario(
                "Profesional Test",
                "prof.service@test.com",
                "123456",
                RolUsuario.PROFESIONAL
        );

        cliente = new Cliente(usuarioCliente, "3001234567");

        profesional = new Profesional(
                usuarioProfesional,
                "Psicología",
                "Profesional de prueba"
        );

        servicio = new Servicio(
                "Consulta general",
                "Consulta inicial",
                30,
                50000.0
        );
    }

    @Test
    void debeAgendarCitaCuandoHorarioEstaDisponible() {
        LocalDate fecha = LocalDate.now().plusDays(1);
        LocalTime hora = LocalTime.of(10, 0);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(profesionalRepository.findById(1L)).thenReturn(Optional.of(profesional));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        when(citaRepository.existsByProfesionalAndFechaAndHora(profesional, fecha, hora))
                .thenReturn(false);

        when(citaRepository.save(any(Cita.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Cita cita = citaService.agendarCita(
                1L,
                1L,
                1L,
                fecha,
                hora,
                "Cita desde test"
        );

        assertNotNull(cita);
        assertEquals(EstadoCita.PENDIENTE, cita.getEstado());
        assertEquals(cliente, cita.getCliente());
        assertEquals(profesional, cita.getProfesional());
        assertEquals(servicio, cita.getServicio());

        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void noDebeAgendarCitaSiHorarioEstaOcupado() {
        LocalDate fecha = LocalDate.now().plusDays(1);
        LocalTime hora = LocalTime.of(11, 0);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(profesionalRepository.findById(1L)).thenReturn(Optional.of(profesional));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        when(citaRepository.existsByProfesionalAndFechaAndHora(profesional, fecha, hora))
                .thenReturn(true);

        assertThrows(CitaNoDisponibleException.class, () -> citaService.agendarCita(
                1L,
                1L,
                1L,
                fecha,
                hora,
                "Horario ocupado"
        ));

        verify(citaRepository, never()).save(any(Cita.class));
    }

    @Test
    void debeConfirmarCitaPendiente() {
        Cita cita = new Cita(
                cliente,
                profesional,
                servicio,
                LocalDate.now().plusDays(1),
                LocalTime.of(9, 0),
                "Confirmación de prueba"
        );

        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(cita)).thenReturn(cita);

        Cita citaConfirmada = citaService.confirmar(1L);

        assertEquals(EstadoCita.CONFIRMADA, citaConfirmada.getEstado());
        verify(citaRepository, times(1)).save(cita);
    }

    @Test
    void debeReprogramarCitaCuandoNuevoHorarioEstaDisponible() {
        LocalDate fechaOriginal = LocalDate.now().plusDays(1);
        LocalTime horaOriginal = LocalTime.of(9, 0);

        LocalDate nuevaFecha = LocalDate.now().plusDays(2);
        LocalTime nuevaHora = LocalTime.of(15, 0);

        Cita cita = new Cita(
                cliente,
                profesional,
                servicio,
                fechaOriginal,
                horaOriginal,
                "Reprogramación de prueba"
        );

        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        when(citaRepository.existsByProfesionalAndFechaAndHoraAndIdNot(
                profesional,
                nuevaFecha,
                nuevaHora,
                cita.getId()
        )).thenReturn(false);

        when(citaRepository.save(cita)).thenReturn(cita);

        Cita citaReprogramada = citaService.reprogramarCita(
                1L,
                nuevaFecha,
                nuevaHora
        );

        assertEquals(nuevaFecha, citaReprogramada.getFecha());
        assertEquals(nuevaHora, citaReprogramada.getHora());
        assertEquals(EstadoCita.PENDIENTE, citaReprogramada.getEstado());

        verify(citaRepository, times(1)).save(cita);
    }
}