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
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class GetAssignmentByIdActivity {

    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    private static final Logger logger = LoggerFactory.getLogger(GetAssignmentByIdActivity.class);


    private AssignmentDAO assignmentDAO;

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable Long id) {

        logger.info("INSIDE GET ASSIGNMENT BY ID CONTROLLER -----");

        Assignment assignments = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            assignmentDAO = new AssignmentDAO(connection);
            assignments = assignmentDAO.getAssignmentById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // You may want to return a more user-friendly error response
        }

        return assignments;
    }
}
