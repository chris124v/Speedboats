package com.tours.services;

import com.tours.business_logic.TourValidator;
import com.tours.models.CreateTourRequest;
import com.tours.models.Tour;
import com.tours.repositories.ToursRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ToursService {

    private final TourValidator validator;
    private final ToursRepository repository;

    public ToursService(TourValidator validator, ToursRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public Tour create(CreateTourRequest request) {
        validator.validate(request);
        return repository.create(request);
    }

    public List<Tour> listAll() {
        return repository.listAll();
    }

    public Optional<Tour> findById(long id) {
        return repository.findById(id);
    }
}
