package com.bookings.repositories;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;

@Singleton
public class DatabaseInitializer {

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
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
                    "CREATE TABLE IF NOT EXISTS bookings (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "tour_id INTEGER NOT NULL, " +
                            "user_id INTEGER NOT NULL, " +
                            "fecha_reserva TEXT NOT NULL, " +
                            "num_personas INTEGER NOT NULL, " +
                            "estado TEXT NOT NULL" +
                            ")"
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
