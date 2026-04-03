package com.bookings.controllers;

import com.bookings.models.ApiError;
import com.bookings.models.Booking;
import com.bookings.models.CreateBookingRequest;
import com.bookings.services.BookingsService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Validated
@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/api/bookings")
public class BookingsController {

    private final BookingsService bookingsService;

    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @Get
    public List<Booking> listBookings() {
        return bookingsService.listAll();
    }

    @Get("/{id}")
    public HttpResponse<?> getBooking(long id) {
        return bookingsService.findById(id)
                .<HttpResponse<?>>map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Post
    public HttpResponse<Booking> createBooking(@Body @Valid CreateBookingRequest request) {
        Booking created = bookingsService.create(request);
        return HttpResponse.created(created);
    }

    @io.micronaut.http.annotation.Error(exception = IllegalArgumentException.class)
    public HttpResponse<ApiError> onIllegalArgument(IllegalArgumentException e) {
        return HttpResponse.badRequest(new ApiError(e.getMessage()));
    }
}
