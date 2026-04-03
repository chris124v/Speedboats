package com.tours.models;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Serdeable
public record CreateTourRequest(
        @NotBlank String nombre,
        @NotNull Double ubicacionLat,
        @NotNull Double ubicacionLng,
        @NotNull @Positive BigDecimal precio,
        @NotNull @Positive Integer duracion,
        @NotNull @Positive Long guiaId
) {
}
