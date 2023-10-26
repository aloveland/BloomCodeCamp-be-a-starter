package com.hcc.requests;

public class GetAssignmentByIdRequest {

    private long assignmentId;

    public GetAssignmentByIdRequest() {
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    @Override
    public String toString() {
        return "GetAssignmentByIdRequest{" +
                "assignmentId=" + assignmentId +
                '}';
    }
}
