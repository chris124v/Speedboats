package com.bookings.http_clients;

import com.bookings.models.TourDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("tours")
public interface ToursClient {

    @Get("/api/tours/{id}")
    TourDto getTour(long id);
}
