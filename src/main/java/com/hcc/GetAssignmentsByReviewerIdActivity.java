package com.hcc;

import com.hcc.DAO.AssignmentDAO;
import com.hcc.DAO.UserDAO;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/review")
public class GetAssignmentsByReviewerIdActivity {

    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    private static final Logger logger = LoggerFactory.getLogger(GetAssignmentsByReviewerIdActivity.class);

    private UserDAO userDAO;

    @GetMapping("/{reviewerName}")
    public List<Assignment> getAssignmentsByReviewerId(@PathVariable String reviewerName) {

        logger.info("INSIDE GET ASSIGNMENTS BY REVIEWER ID CONTROLLER -----");

        List<Assignment> assignments = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            AssignmentDAO assignmentDAO = new AssignmentDAO(connection);
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.getUserByUsername(reviewerName);
            logger.info("USER PRINT----- {}", user);
            assignments = assignmentDAO.getAssignmentsByReviewId(user.getId());
        } catch (SQLException e) {
            logger.error("SQL Exception when getting assignments by reviewer id: " + reviewerName, e);

        }

        return assignments;
    }
}
