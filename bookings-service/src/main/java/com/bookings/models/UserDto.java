package com.bookings.models;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserDto(
        Long id,
        String nombre,
        String email,
        String telefono,
        String tipo
) {
}
