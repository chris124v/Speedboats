package com.tours.repositories;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Singleton
public class DatabaseInitializer {

    private final DataSource dataSource;
    private final ToursRepository toursRepository;

    public DatabaseInitializer(DataSource dataSource, ToursRepository toursRepository) {
        this.dataSource = dataSource;
        this.toursRepository = toursRepository;
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
                    "CREATE TABLE IF NOT EXISTS tours (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "nombre TEXT NOT NULL, " +
                            "ubicacion_lat REAL NOT NULL, " +
                            "ubicacion_lng REAL NOT NULL, " +
                            "precio REAL NOT NULL, " +
                            "duracion INTEGER NOT NULL, " +
                            "guia_id INTEGER NOT NULL" +
                            ")"
            );

            if (toursRepository.count() == 0) {
                seed(connection);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private void seed(Connection connection) throws Exception {
        String sql = "INSERT INTO tours (nombre, ubicacion_lat, ubicacion_lng, precio, duracion, guia_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Tour Manglares");
            statement.setDouble(2, 9.963);
            statement.setDouble(3, -84.778);
            statement.setDouble(4, 55.0);
            statement.setInt(5, 90);
            statement.setLong(6, 1);
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Atardecer en la Bahía");
            statement.setDouble(2, 9.931);
            statement.setDouble(3, -84.707);
            statement.setDouble(4, 70.0);
            statement.setInt(5, 120);
            statement.setLong(6, 1);
            statement.executeUpdate();
        }
    }
}
