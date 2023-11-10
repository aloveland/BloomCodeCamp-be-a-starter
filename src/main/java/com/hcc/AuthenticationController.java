package com.hcc;

import com.hcc.DAO.AssignmentDAO;
import com.hcc.DAO.UserDAO;
import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.requests.AuthenticationRequest;
import com.hcc.services.UserDetailServiceImpl;
import com.hcc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");
    @Autowired
    private AuthenticationManager authenticationManager;


    private JWTUtils jwtUtil;


    private UserDetailServiceImpl userDetailsService;

    private UserDAO userDao;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        jwtUtil = new JWTUtils();
        authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
        userDetailsService = new UserDetailServiceImpl();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            userDao = new UserDAO(connection);

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
                );
            } catch (BadCredentialsException e) {
                throw new Exception("Incorrect username or password", e);
            }

            final User user = userDao.getUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtUtil.generateToken(user);
            //adding for commit
            return ResponseEntity.ok(new com.hcc.AuthenticationResponse(jwt));
        }
    }
}
