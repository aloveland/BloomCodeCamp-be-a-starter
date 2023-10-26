package com.hcc.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcc.DAO.AssignmentDAO;
import com.hcc.DTO.AuthCredentialRequest;
import com.hcc.entities.User;
import com.hcc.exceptions.AuthenticationException;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class LoginActivity implements RequestHandler<AuthCredentialRequest, String> {

    private static ConfigurableApplicationContext context;

    static {
        SpringApplication app = new SpringApplication(LoginActivity.class);
        String activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");
        if (activeProfile != null && !activeProfile.isEmpty()) {
            app.setAdditionalProfiles(activeProfile);
        }
        context = app.run();
    }

    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private JwtUtil jwtUtil;

    @Override
    public String handleRequest(AuthCredentialRequest request, Context lambdaContext) {

        System.out.println("Active profile: " + activeProfile);

        String token = null;
        jwtUtil = new JwtUtil();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
            AssignmentDAO assignmentDAO = new AssignmentDAO(connection);
            boolean isAuthenticated = assignmentDAO.authenticate(request.getUsername(), request.getPassword());

            if(isAuthenticated) {
                User user = retrieveUserByUsername(request.getUsername());
                token = jwtUtil.generateToken(user);
            } else {
                throw new AuthenticationException("Authentication failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
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
