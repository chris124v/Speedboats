package com.users.models;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record User(
        Long id,
        String nombre,
        String email,
        String telefono,
        String tipo
) {
}
