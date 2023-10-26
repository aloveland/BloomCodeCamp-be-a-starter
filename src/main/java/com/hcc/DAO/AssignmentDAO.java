package com.hcc.DAO;

import com.hcc.entities.Assignment;
import com.hcc.exceptions.AuthenticationException;
import com.hcc.exceptions.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

     private Connection connection;

     public AssignmentDAO(Connection connection) {
          this.connection = connection;

     }

     public List<Assignment> getAssignmentByUser(Long user) {
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
                    assignment.setId((long) resultSet.getInt("user_id"));
                    assignment.setCodeReviewerId((long) resultSet.getInt("code_reviewer_id"));

                    results.add(assignment);
               }

          } catch (SQLException e) {
               throw new RuntimeException(e);
          }
          return results;
     }

     public List<Assignment> getAssignmentById(Long id) {
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
                    assignment.setId((long) resultSet.getInt("user_id"));
                    assignment.setCodeReviewerId((long) resultSet.getInt("code_reviewer_id"));
                    results.add(assignment);
               }
          } catch (SQLException e) {
               throw new RuntimeException(e);
          }
          return results;
     }

     public Assignment putAssignment(Assignment assignment) {

          String insertQuery = "INSERT INTO assignments (id, branch, code_review_video_url, github_url, number, user_id, code_reviewer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

          try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
               preparedStatement.setLong(1, assignment.getId());
               preparedStatement.setString(2, assignment.getBranch());
               preparedStatement.setString(3, assignment.getReviewVideoUrl());
               preparedStatement.setString(4, assignment.getGithubUrl());
               preparedStatement.setInt(5, assignment.getNumber());
               preparedStatement.setLong(6, assignment.getUserId());
               preparedStatement.setLong(7, assignment.getCodeReviewerId());

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
}

