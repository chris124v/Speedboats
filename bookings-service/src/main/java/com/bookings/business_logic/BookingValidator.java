package com.bookings.business_logic;

import com.bookings.models.CreateBookingRequest;
import jakarta.inject.Singleton;

import java.time.LocalDate;

@Singleton
public class BookingValidator {

    public void validate(CreateBookingRequest request) {
        String estadoUpper = request.estado().trim().toUpperCase();
        if (!estadoUpper.equals("PENDING") && !estadoUpper.equals("CONFIRMED") && !estadoUpper.equals("CANCELLED")) {
            throw new IllegalArgumentException("estado must be PENDING, CONFIRMED, or CANCELLED");
        }
        if (request.fechaReserva().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("fechaReserva cannot be in the past");
        }
    }
}
