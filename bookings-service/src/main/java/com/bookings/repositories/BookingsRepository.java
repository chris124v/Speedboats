package com.bookings.repositories;

import com.bookings.models.Booking;
import com.bookings.models.CreateBookingRequest;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class BookingsRepository {

    private final DataSource dataSource;

    public BookingsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Booking create(CreateBookingRequest request) {
        String sql = "INSERT INTO bookings (tour_id, user_id, fecha_reserva, num_personas, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, request.tourId());
            statement.setLong(2, request.userId());
            statement.setString(3, request.fechaReserva().toString());
            statement.setInt(4, request.numPersonas());
            statement.setString(5, request.estado().trim().toUpperCase());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return new Booking(id, request.tourId(), request.userId(), request.fechaReserva(), request.numPersonas(), request.estado().trim().toUpperCase());
                }
            }

            throw new IllegalStateException("Failed to generate id for booking");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> listAll() {
        String sql = "SELECT id, tour_id, user_id, fecha_reserva, num_personas, estado FROM bookings ORDER BY id DESC";
        List<Booking> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Booking> findById(long id) {
        String sql = "SELECT id, tour_id, user_id, fecha_reserva, num_personas, estado FROM bookings WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long tourId = rs.getLong("tour_id");
        long userId = rs.getLong("user_id");
        LocalDate fecha = LocalDate.parse(rs.getString("fecha_reserva"));
        int num = rs.getInt("num_personas");
        String estado = rs.getString("estado");
        return new Booking(id, tourId, userId, fecha, num, estado);
    }
}
