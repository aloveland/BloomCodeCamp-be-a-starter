package com.hcc;

import com.hcc.DAO.AssignmentDAO;
import com.hcc.entities.Assignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
@RequestMapping("/assignments")
public class PutAssignmentActivity {

    private static final Logger logger = LoggerFactory.getLogger(PutAssignmentActivity.class);
    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    private AssignmentDAO assignmentDAO;

    @PutMapping("/{id}")
    public Assignment updateAssignmentById(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        logger.info("INSIDE UPDATE ASSIGNMENT BY ID CONTROLLER -----");
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            assignmentDAO = new AssignmentDAO(connection);
            // Load the existing assignment
            Assignment existingAssignment = assignmentDAO.getAssignmentById(id);
            if (existingAssignment == null) {
                // Handle not found error, possibly throw an exception or return an error response
                return null;
            }

            // Update the fields of the existing assignment with the values from updatedAssignment
            existingAssignment.setBranch(updatedAssignment.getBranch());
            existingAssignment.setReviewVideoUrl(updatedAssignment.getReviewVideoUrl());
            existingAssignment.setGithubUrl(updatedAssignment.getGithubUrl());
            existingAssignment.setNumber(updatedAssignment.getNumber());
            existingAssignment.setUserId(updatedAssignment.getUserId());
            existingAssignment.setCodeReviewerId(updatedAssignment.getCodeReviewerId());
            existingAssignment.setReviewVideoUrl(updatedAssignment.getReviewVideoUrl());
            existingAssignment.setStatus(updatedAssignment.getStatus());

            // Save the updated assignment
            Assignment savedAssignment = assignmentDAO.updateAssignment(existingAssignment);

            return savedAssignment;
        }catch (SQLException e) {
            e.printStackTrace();
            // You may want to return a more user-friendly error response
        }
        return new Assignment();
    }


}
