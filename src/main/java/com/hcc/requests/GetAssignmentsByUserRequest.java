package com.hcc.requests;

public class GetAssignmentsByUserRequest {

    private long userId;

    public GetAssignmentsByUserRequest() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GetAssignmentsByUserRequest{" +
                "userId=" + userId +
                '}';
    }
}
