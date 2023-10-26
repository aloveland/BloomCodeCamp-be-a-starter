package com.hcc.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcc.DAO.AssignmentDAO;
import com.hcc.entities.Assignment;
import com.hcc.requests.GetAssignmentByIdRequest; // Assuming this is the name of your new request object

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class GetAssignmentByIdActivity implements RequestHandler<GetAssignmentByIdRequest, List<Assignment>> {

    // JDBC connection parameters (replace with your RDS details)
    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    @Override
    public List<Assignment> handleRequest(GetAssignmentByIdRequest request, Context context) {
        List<Assignment> result = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
            AssignmentDAO assignmentDAO = new AssignmentDAO(connection);
            result = assignmentDAO.getAssignmentById(request.getAssignmentId()); // Assuming this is the method in your DAO and the method in your request object
        } catch (Exception e) {
            // Handle exception (e.g., log the error, etc.)
            e.printStackTrace();
        }

        return result;
    }
}
