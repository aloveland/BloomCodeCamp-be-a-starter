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
@RequestMapping("/assignments")
public class GetAssignmentsByUserActivity {

    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    private static final Logger logger = LoggerFactory.getLogger(GetAssignmentsByUserActivity.class);

    private AssignmentDAO assignmentDAO;
    private UserDAO userDAO;

    @PostMapping
    public List<Assignment> getAssignmentsByUser(@RequestBody UserRequest userRequest) {
        logger.info("INSIDE GET ASSIGNMENTS BY USER CONTROLLER ----- {}", userRequest.getUsername());


        List<Assignment> assignments = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            assignmentDAO = new AssignmentDAO(connection);
            userDAO = new UserDAO(connection);
            User user = userDAO.getUserByUsername(userRequest.getUsername());
            logger.info("USER PRINT----- {}", user);
            assignments = assignmentDAO.getAssignmentsByUser(user.getId());

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return assignments;
    }


    public static class UserRequest {
        private Long userId;
        private String username;
        public String getUsername(){
            return username;
        }
        public void setUsername(String username){
            this.username = username;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
