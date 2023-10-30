package com.hcc;

import com.hcc.DAO.AssignmentDAO;
import com.hcc.entities.Assignment;
import com.hcc.lambda.GetAssignmentByUserActivity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class BackendApplicationTests {

	private AssignmentDAO assignmentDAO;
	@Test
	void contextLoads() {
	}
	/*
	@Test
	public void testGetAssignmentByUser() throws SQLException {
		String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/bloomtechlabs";
		String DB_USER = "postgres";
		String DB_PASSWORD = System.getenv("password");
		List<Assignment> result;
		try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			assignmentDAO = new AssignmentDAO(connection);
			result = assignmentDAO.getAssignmentByUser(1L);
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertFalse(result.isEmpty(), "No assignments found for the given user.");
	}
	 */
}
