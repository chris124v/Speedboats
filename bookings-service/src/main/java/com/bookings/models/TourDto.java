package com.bookings.models;

import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;

@Serdeable
public record TourDto(
        Long id,
        String nombre,
        double ubicacionLat,
        double ubicacionLng,
        BigDecimal precio,
        int duracion,
        Long guiaId
) {
}
