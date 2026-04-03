package com.tours.business_logic;

import com.tours.models.CreateTourRequest;
import jakarta.inject.Singleton;

@Singleton
public class TourValidator {

    public void validate(CreateTourRequest request) {
        if (request.ubicacionLat() < -90 || request.ubicacionLat() > 90) {
            throw new IllegalArgumentException("ubicacionLat must be between -90 and 90");
        }
        if (request.ubicacionLng() < -180 || request.ubicacionLng() > 180) {
            throw new IllegalArgumentException("ubicacionLng must be between -180 and 180");
        }
    }
}
