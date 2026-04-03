package com.bookings.models;

import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDate;

@Serdeable
public record Booking(
        Long id,
        Long tourId,
        Long userId,
        LocalDate fechaReserva,
        int numPersonas,
        String estado
) {
}
