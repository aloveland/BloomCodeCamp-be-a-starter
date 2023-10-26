package com.hcc.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcc.DAO.AssignmentDAO;
import com.hcc.entities.Assignment;
import com.hcc.requests.PutAssignmentRequest; // Assuming you'll have a PutAssignmentRequest class

import java.sql.Connection;
import java.sql.DriverManager;

public class PutAssignmentActivity implements RequestHandler<PutAssignmentRequest, Assignment> {

    // JDBC connection parameters (replace with your RDS details)
    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    @Override
    public Assignment handleRequest(PutAssignmentRequest request, Context context) {
        Assignment updatedAssignment = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
            AssignmentDAO assignmentDAO = new AssignmentDAO(connection);
            Assignment assignment = request.toAssignment();
            updatedAssignment = assignmentDAO.putAssignment(assignment); // Assuming updateAssignment method in your DAO and getAssignment in your request object
        } catch (Exception e) {
            // Handle exception (e.g., log the error, etc.)
            e.printStackTrace();
        }

        return updatedAssignment;
    }
}
