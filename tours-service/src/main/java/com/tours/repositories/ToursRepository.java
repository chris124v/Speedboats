package com.tours.repositories;

import com.tours.models.CreateTourRequest;
import com.tours.models.Tour;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ToursRepository {

    private final DataSource dataSource;

    public ToursRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Tour create(CreateTourRequest request) {
        String sql = "INSERT INTO tours (nombre, ubicacion_lat, ubicacion_lng, precio, duracion, guia_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, request.nombre());
            statement.setDouble(2, request.ubicacionLat());
            statement.setDouble(3, request.ubicacionLng());
            statement.setBigDecimal(4, request.precio());
            statement.setInt(5, request.duracion());
            statement.setLong(6, request.guiaId());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return new Tour(id, request.nombre(), request.ubicacionLat(), request.ubicacionLng(), request.precio(), request.duracion(), request.guiaId());
                }
            }

            throw new IllegalStateException("Failed to generate id for tour");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tour> listAll() {
        String sql = "SELECT id, nombre, ubicacion_lat, ubicacion_lng, precio, duracion, guia_id FROM tours ORDER BY id DESC";
        List<Tour> result = new ArrayList<>();

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

    public Optional<Tour> findById(long id) {
        String sql = "SELECT id, nombre, ubicacion_lat, ubicacion_lng, precio, duracion, guia_id FROM tours WHERE id = ?";

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

    public long count() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(1) FROM tours");
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Tour mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String nombre = rs.getString("nombre");
        double lat = rs.getDouble("ubicacion_lat");
        double lng = rs.getDouble("ubicacion_lng");
        BigDecimal precio = rs.getBigDecimal("precio");
        int duracion = rs.getInt("duracion");
        long guiaId = rs.getLong("guia_id");
        return new Tour(id, nombre, lat, lng, precio, duracion, guiaId);
    }
}
