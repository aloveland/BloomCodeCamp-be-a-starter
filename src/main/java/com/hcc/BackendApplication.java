package com.hcc;

import com.hcc.DAO.UserDAO;
import com.hcc.controllers.CreateUserActivity;
import com.hcc.entities.User;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hibernate.jpa.AvailableSettings.JDBC_URL;


@SpringBootApplication
@ComponentScan(basePackages = {"com.hcc"})
public class BackendApplication {
	private static final Logger logger = LoggerFactory.getLogger(CreateUserActivity.class);

	private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = System.getenv("password");
	public static void main(String[] args) {
		User user = new User();
		user.setUsername("alove");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pass = passwordEncoder.encode("test");
		user.setPassword(pass);
		try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
			UserDAO userDAO = new UserDAO(connection);
			//userDAO.createUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("STARTING APPLICATION---------------");
		SpringApplication.run(BackendApplication.class, args);
	}

}
