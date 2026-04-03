package com.users.controllers;

import com.users.models.ApiError;
import com.users.models.CreateUserRequest;
import com.users.models.User;
import com.users.services.UsersService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Validated
@Controller("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Get
    public List<User> listUsers() {
        return usersService.listAll();
    }

    @Get("/{id}")
    public HttpResponse<?> getUser(long id) {
        return usersService.findById(id)
                .<HttpResponse<?>>map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Post
    public HttpResponse<User> createUser(@Body @Valid CreateUserRequest request) {
        User created = usersService.create(request);
        return HttpResponse.created(created);
    }

    @io.micronaut.http.annotation.Error(exception = IllegalArgumentException.class)
    public HttpResponse<ApiError> onIllegalArgument(IllegalArgumentException e) {
        return HttpResponse.badRequest(new ApiError(e.getMessage()));
    }
}
