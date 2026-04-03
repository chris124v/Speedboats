package com.users.business_logic;

import com.users.models.CreateUserRequest;
import jakarta.inject.Singleton;

@Singleton
public class UserValidator {

    public void validate(CreateUserRequest request) {
        String tipoUpper = request.tipo().trim().toUpperCase();
        if (!tipoUpper.equals("GUIDE") && !tipoUpper.equals("USER")) {
            throw new IllegalArgumentException("tipo must be GUIDE or USER");
        }
    }
}
