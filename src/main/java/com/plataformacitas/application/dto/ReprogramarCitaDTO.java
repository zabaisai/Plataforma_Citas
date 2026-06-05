package com.plataformacitas.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReprogramarCitaDTO {

    private Long citaId;
    private LocalDate nuevaFecha;
    private LocalTime nuevaHora;

    public ReprogramarCitaDTO() {
    }

    public ReprogramarCitaDTO(Long citaId, LocalDate nuevaFecha, LocalTime nuevaHora) {
        this.citaId = citaId;
        this.nuevaFecha = nuevaFecha;
        this.nuevaHora = nuevaHora;
    }

    public boolean datosCompletos() {
        return citaId != null && nuevaFecha != null && nuevaHora != null;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public LocalDate getNuevaFecha() {
        return nuevaFecha;
    }

    public void setNuevaFecha(LocalDate nuevaFecha) {
        this.nuevaFecha = nuevaFecha;
    }

    public LocalTime getNuevaHora() {
        return nuevaHora;
    }

    public void setNuevaHora(LocalTime nuevaHora) {
        this.nuevaHora = nuevaHora;
    }
}