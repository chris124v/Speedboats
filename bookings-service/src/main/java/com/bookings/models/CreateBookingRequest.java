package com.bookings.models;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Serdeable
public record CreateBookingRequest(
        @NotNull @Positive Long tourId,
        @NotNull @Positive Long userId,
        @NotNull LocalDate fechaReserva,
        @NotNull @Positive Integer numPersonas,
        @NotNull String estado
) {
}
