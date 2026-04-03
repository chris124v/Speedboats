package com.tours.controllers;

import com.tours.models.ApiError;
import com.tours.models.CreateTourRequest;
import com.tours.models.Tour;
import com.tours.services.ToursService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Validated
@Controller("/api/tours")
public class ToursController {

    private final ToursService toursService;

    public ToursController(ToursService toursService) {
        this.toursService = toursService;
    }

    @Get
    public List<Tour> listTours() {
        return toursService.listAll();
    }

    @Get("/{id}")
    public HttpResponse<?> getTour(long id) {
        return toursService.findById(id)
                .<HttpResponse<?>>map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Post
    public HttpResponse<Tour> createTour(@Body @Valid CreateTourRequest request) {
        Tour created = toursService.create(request);
        return HttpResponse.created(created);
    }

    @io.micronaut.http.annotation.Error(exception = IllegalArgumentException.class)
    public HttpResponse<ApiError> onIllegalArgument(IllegalArgumentException e) {
        return HttpResponse.badRequest(new ApiError(e.getMessage()));
    }
}
