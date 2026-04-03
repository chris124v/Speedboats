package com.users.repositories;

import com.users.models.CreateUserRequest;
import com.users.models.User;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class UsersRepository {

    private final DataSource dataSource;

    public UsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(CreateUserRequest request) {
        String sql = "INSERT INTO users (nombre, email, telefono, tipo) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, request.nombre());
            statement.setString(2, request.email());
            statement.setString(3, request.telefono());
            statement.setString(4, request.tipo().trim().toUpperCase());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return new User(id, request.nombre(), request.email(), request.telefono(), request.tipo().trim().toUpperCase());
                }
            }

            throw new IllegalStateException("Failed to generate id for user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> listAll() {
        String sql = "SELECT id, nombre, email, telefono, tipo FROM users ORDER BY id DESC";
        List<User> result = new ArrayList<>();

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

    public Optional<User> findById(long id) {
        String sql = "SELECT id, nombre, email, telefono, tipo FROM users WHERE id = ?";

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
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(1) FROM users");
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String nombre = rs.getString("nombre");
        String email = rs.getString("email");
        String telefono = rs.getString("telefono");
        String tipo = rs.getString("tipo");
        return new User(id, nombre, email, telefono, tipo);
    }
}
