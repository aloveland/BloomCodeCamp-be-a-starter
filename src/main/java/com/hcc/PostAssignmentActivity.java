package com.hcc;

import com.hcc.DAO.AssignmentDAO;
import com.hcc.entities.Assignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
@RequestMapping("/assignments")
public class PostAssignmentActivity {

    private static final Logger logger = LoggerFactory.getLogger(PostAssignmentActivity.class);
    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    private AssignmentDAO assignmentDAO;

    @PostMapping("/")
    public Assignment createAssignment(@RequestBody Assignment newAssignment) {
        logger.info("INSIDE CREATE ASSIGNMENT CONTROLLER -----");
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            assignmentDAO = new AssignmentDAO(connection);



            return assignmentDAO.saveAssignment(newAssignment);
        }catch (SQLException e) {
            e.printStackTrace();

        }
        return new Assignment();
    }

}
