package com.hcc.DAO;

import com.hcc.entities.User;
import com.hcc.exceptions.AuthenticationException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public User getUserById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                // ... set other User fields ...
                return user;
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> results = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                // ... set other User fields ...
                results.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public void createUser(User user) {
        String insertQuery = "INSERT INTO users (cohort_start_date, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            // ... set other fields ...
            Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
            preparedStatement.setDate(1,sqlDate);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticate(String username, String password) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND PASSWORD = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new AuthenticationException("Username or password incorrect");
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
