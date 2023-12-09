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
		
		Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
		assignmentDAO = new AssignmentDAO(connection);


		assignmentDAO = new AssignmentDAO(connection);


		Assignment existingAssignment = assignmentDAO.getAssignmentById(1L);
		existingAssignment.setBranch("newBranch");


		Assignment updatedAssignment = assignmentDAO.updateAssignment(existingAssignment);


		assertNotNull(updatedAssignment);
		assertEquals("newBranch", updatedAssignment.getBranch());


		connection.close();
	}

	 */
}
