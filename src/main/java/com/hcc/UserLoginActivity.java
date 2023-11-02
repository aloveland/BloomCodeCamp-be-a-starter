package com.hcc;

import com.hcc.DAO.AssignmentDAO;
import com.hcc.DTO.AuthCredentialRequest;
import com.hcc.entities.User;
import com.hcc.exceptions.AuthenticationException;
import com.hcc.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
@RequestMapping("/login")
public class UserLoginActivity {

    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    private static final Logger logger = LoggerFactory.getLogger(UserLoginActivity.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public String login(@RequestBody AuthCredentialRequest request) {

        logger.info("INSIDE LOGIN CONTROLLER -----");

        System.out.println("Active profile: " + activeProfile);

        String token = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
            AssignmentDAO assignmentDAO = new AssignmentDAO(connection);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            System.out.println("ENCODING ----> " + String.valueOf(passwordEncoder.encode("test")));
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            boolean isAuthenticated = assignmentDAO.authenticate(request.getUsername(), hashedPassword);

            if(isAuthenticated) {
                User user = retrieveUserByUsername(request.getUsername());
                token = jwtUtil.generateToken(user);
            } else {
                throw new AuthenticationException("Authentication failed");
            }
        } catch (SQLException | AuthenticationException e) {
            e.printStackTrace();
        }

        return token;
    }

    private User retrieveUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        // Set other user properties as needed
        return user;
    }


}