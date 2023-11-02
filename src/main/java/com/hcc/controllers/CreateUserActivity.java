package com.hcc.controllers;

import com.hcc.DAO.UserDAO;
import com.hcc.requests.CreateUserRequest;
import com.hcc.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
@RequestMapping("/createUser")
public class CreateUserActivity {


    private static final Logger logger = LoggerFactory.getLogger(CreateUserActivity.class);
    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");


    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {
        logger.info("Received request to create user: {}", request);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername(request.getUsername());

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
            UserDAO userDAO = new UserDAO(connection);
            userDAO.createUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


}
