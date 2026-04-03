package com.tours.models;

import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;

@Serdeable
public record Tour(
        Long id,
        String nombre,
        double ubicacionLat,
        double ubicacionLng,
        BigDecimal precio,
        int duracion,
        Long guiaId
) {
}
