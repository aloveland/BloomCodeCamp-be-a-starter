package com.hcc.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcc.DAO.AssignmentDAO;
import com.hcc.entities.Assignment;
import com.hcc.requests.GetAssignmentsByUserRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetAssignmentByUserActivity implements RequestHandler<GetAssignmentsByUserRequest, List<Assignment>> {

    // JDBC connection parameters (replace with your RDS details)
    private static final String JDBC_URL = "jdbc:postgresql://bloomtechlabs.c48jjdfk1at5.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv("password");

    @Override
    public List<Assignment> handleRequest(GetAssignmentsByUserRequest request, Context context) {
        List<Assignment> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
             AssignmentDAO assignmentDAO = new AssignmentDAO(connection);
             results = assignmentDAO.getAssignmentByUser(request.getUserId());
        } catch (Exception e) {
            // Handle exception (e.g., log the error, etc.)
            e.printStackTrace();
        }

        return results;
    }
}
