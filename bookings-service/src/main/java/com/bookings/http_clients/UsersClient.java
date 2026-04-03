package com.bookings.http_clients;

import com.bookings.models.UserDto;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("users")
public interface UsersClient {

    @Get("/api/users/{id}")
    UserDto getUser(long id);
}
