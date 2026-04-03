package com.users.services;

import com.users.business_logic.UserValidator;
import com.users.models.CreateUserRequest;
import com.users.models.User;
import com.users.repositories.UsersRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class UsersService {

    private final UserValidator validator;
    private final UsersRepository repository;

    public UsersService(UserValidator validator, UsersRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public User create(CreateUserRequest request) {
        validator.validate(request);
        return repository.create(request);
    }

    public List<User> listAll() {
        return repository.listAll();
    }

    public Optional<User> findById(long id) {
        return repository.findById(id);
    }
}
