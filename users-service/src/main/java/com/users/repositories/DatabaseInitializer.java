package com.users.repositories;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Singleton
public class DatabaseInitializer {

    private final DataSource dataSource;
    private final UsersRepository usersRepository;

    public DatabaseInitializer(DataSource dataSource, UsersRepository usersRepository) {
        this.dataSource = dataSource;
        this.usersRepository = usersRepository;
    }

    @EventListener
    public void onStartup(StartupEvent event) {
        try {
            Files.createDirectories(Path.of("data"));
        } catch (Exception e) {
            throw new RuntimeException("Unable to create data directory", e);
        }

        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "nombre TEXT NOT NULL, " +
                            "email TEXT NOT NULL, " +
                            "telefono TEXT NOT NULL, " +
                            "tipo TEXT NOT NULL" +
                            ")"
            );

            if (usersRepository.count() == 0) {
                seed(connection);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private void seed(Connection connection) throws Exception {
        String sql = "INSERT INTO users (nombre, email, telefono, tipo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Guia Local");
            statement.setString(2, "guia@example.com");
            statement.setString(3, "8888-0001");
            statement.setString(4, "GUIDE");
            statement.executeUpdate();
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Usuario Demo");
            statement.setString(2, "usuario@example.com");
            statement.setString(3, "8888-0002");
            statement.setString(4, "USER");
            statement.executeUpdate();
        }
    }
}
