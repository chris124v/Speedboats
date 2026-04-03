package com.bookings.services;

import com.bookings.business_logic.BookingValidator;
import com.bookings.http_clients.ToursClient;
import com.bookings.http_clients.UsersClient;
import com.bookings.models.Booking;
import com.bookings.models.CreateBookingRequest;
import com.bookings.repositories.BookingsRepository;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class BookingsService {

    private final BookingValidator validator;
    private final BookingsRepository repository;
    private final ToursClient toursClient;
    private final UsersClient usersClient;

    public BookingsService(BookingValidator validator, BookingsRepository repository, ToursClient toursClient, UsersClient usersClient) {
        this.validator = validator;
        this.repository = repository;
        this.toursClient = toursClient;
        this.usersClient = usersClient;
    }

    public Booking create(CreateBookingRequest request) {
        validator.validate(request);
        validateReferencesExist(request.tourId(), request.userId());
        return repository.create(request);
    }

    public List<Booking> listAll() {
        return repository.listAll();
    }

    public Optional<Booking> findById(long id) {
        return repository.findById(id);
    }

    private void validateReferencesExist(long tourId, long userId) {
        try {
            toursClient.getTour(tourId);
        } catch (HttpClientResponseException e) {
            if (e.getStatus().getCode() == 404) {
                throw new IllegalArgumentException("tourId does not exist");
            }
            throw e;
        }

        try {
            usersClient.getUser(userId);
        } catch (HttpClientResponseException e) {
            if (e.getStatus().getCode() == 404) {
                throw new IllegalArgumentException("userId does not exist");
            }
            throw e;
        }
    }
}
