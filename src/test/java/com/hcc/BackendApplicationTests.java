package com.hcc;

import com.hcc.DAO.AssignmentDAO;
import com.hcc.entities.Assignment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BackendApplicationTests {

	/*
	private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = System.getenv("password");

	private AssignmentDAO assignmentDAO;

	@Test
	void updateAssignment() throws SQLException {
		// Establish a connection to your database
		Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
		assignmentDAO = new AssignmentDAO(connection);

		// Create an instance of AssignmentDAO
		assignmentDAO = new AssignmentDAO(connection);

		// Prepare your test data
		Assignment existingAssignment = assignmentDAO.getAssignmentById(1L);
		existingAssignment.setBranch("newBranch");

		// Call the method under test
		Assignment updatedAssignment = assignmentDAO.updateAssignment(existingAssignment);

		// Assert the outcome
		assertNotNull(updatedAssignment);
		assertEquals("newBranch", updatedAssignment.getBranch());

		// Close the connection
		connection.close();
	}

	 */
}
