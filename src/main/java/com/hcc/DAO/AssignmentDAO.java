package com.hcc.DAO;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.exceptions.AuthenticationException;
import com.hcc.exceptions.ResourceNotFoundException;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class AssignmentDAO {

    private Connection connection;
    private UserDAO userDAO;

    public AssignmentDAO(Connection connection) {
        this.connection = connection;
        userDAO = new UserDAO(connection);
    }

    public List<Assignment> getAssignmentsByUser(Long user) {
        List<Assignment> results = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM assignments WHERE user_id = ?")) {
            preparedStatement.setLong(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Assignment assignment = new Assignment();

                assignment.setId((long) resultSet.getInt("id"));
                assignment.setBranch(resultSet.getString("branch"));
                assignment.setReviewVideoUrl(resultSet.getString("code_review_video_url"));
                assignment.setGithubUrl(resultSet.getString("github_url"));
                assignment.setNumber(resultSet.getInt("number"));
                assignment.setUserId((long) resultSet.getInt("user_id"));
                assignment.setCodeReviewerId((long) resultSet.getInt("code_reviewer_id"));
                assignment.setStatus(resultSet.getString("status"));

                results.add(assignment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public Assignment getAssignmentById(Long id) {
        List<Assignment> results = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM assignments WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Assignment assignment = new Assignment();
                assignment.setId((long) resultSet.getInt("id"));
                assignment.setBranch(resultSet.getString("branch"));
                assignment.setReviewVideoUrl(resultSet.getString("code_review_video_url"));
                assignment.setGithubUrl(resultSet.getString("github_url"));
                assignment.setNumber(resultSet.getInt("number"));
                assignment.setUserId((long) resultSet.getInt("user_id"));
                assignment.setCodeReviewerId((long) resultSet.getInt("code_reviewer_id"));
                assignment.setStatus(resultSet.getString("status"));
                results.add(assignment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results.get(0);
    }

    public Assignment putAssignment(Assignment assignment) {

        String insertQuery = "INSERT INTO assignments (id, branch, code_review_video_url, github_url, number, user_id, code_reviewer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setLong(1, assignment.getId());
            preparedStatement.setString(2, assignment.getBranch());
            preparedStatement.setString(3, assignment.getReviewVideoUrl());
            preparedStatement.setString(4, assignment.getGithubUrl());
            preparedStatement.setInt(5, assignment.getNumber());
            Long userId = assignment.getUserId();
            Long reviewerId = assignment.getCodeReviewerId();

            if (userId != null && reviewerId != null) {
                preparedStatement.setLong(6, userId);
                preparedStatement.setLong(7, reviewerId);
            } else {
                throw new IllegalArgumentException("User and CodeReviewer cannot be null");
            }

            int rowsInserted = preparedStatement.executeUpdate();

            return assignment;

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
                System.out.println(resultSet);
                throw new AuthenticationException("Username or password incorrect");
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public Assignment updateAssignment(Assignment assignment) {
        String updateQuery = "UPDATE assignments SET branch = ?, code_review_video_url = ?, github_url = ?, number = ?, user_id = ?, code_reviewer_id = ?, status = ? " +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, assignment.getBranch());
            preparedStatement.setString(2, assignment.getReviewVideoUrl());
            preparedStatement.setString(3, assignment.getGithubUrl());
            preparedStatement.setInt(4, assignment.getNumber());
            Long user = assignment.getUserId();
            Long codeReviewer = assignment.getCodeReviewerId();
            if (user != null && codeReviewer != null) {
                preparedStatement.setLong(5, user);
                preparedStatement.setLong(6, codeReviewer);
            } else {
                throw new IllegalArgumentException("User and CodeReviewer cannot be null");
            }
            preparedStatement.setString(7, assignment.getStatus());
            preparedStatement.setLong(8, assignment.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assignment;
    }

    public Assignment saveAssignment(Assignment assignment) {
        String insertQuery = "INSERT INTO assignments " +
                "(branch, code_review_video_url, github_url, number, user_id, code_reviewer_id, review_video_url, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, assignment.getBranch());
            preparedStatement.setString(2, assignment.getReviewVideoUrl());
            preparedStatement.setString(3, assignment.getGithubUrl());
            preparedStatement.setInt(4, assignment.getNumber());

            Long user = assignment.getUserId();
            Long codeReviewer = assignment.getCodeReviewerId();

            if (user != null && codeReviewer != null) {
                preparedStatement.setLong(5, user);
                preparedStatement.setLong(6, codeReviewer);
            } else {
                throw new IllegalArgumentException("User and CodeReviewer cannot be null");
            }


            preparedStatement.setString(7, assignment.getReviewVideoUrl());
            preparedStatement.setString(8, assignment.getStatus());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assignment.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return assignment;
    }
}
