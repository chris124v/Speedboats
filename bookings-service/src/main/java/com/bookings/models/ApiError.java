package com.bookings.models;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ApiError(String message) {
}
