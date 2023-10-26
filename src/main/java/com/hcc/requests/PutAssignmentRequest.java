package com.hcc.requests;

import com.hcc.entities.Assignment;

public class PutAssignmentRequest {

    private Long id;
    private String status;
    private Integer number;
    private String githubUrl;
    private String branch;
    private String codereviewVideoUrl;
    private Long userId;
    private Long codeReviewerId;

    public PutAssignmentRequest() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getReviewVideoUrl() {
        return codereviewVideoUrl;
    }

    public void setReviewVideoUrl(String reviewVideoUrl) {
        this.codereviewVideoUrl = reviewVideoUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCodeReviewerId() {
        return codeReviewerId;
    }

    public void setCodeReviewerId(Long codeReviewer) {
        this.codeReviewerId = codeReviewer;
    }
    public Assignment toAssignment() {
        Assignment assignment = new Assignment();
        assignment.setId(this.id);
        assignment.setStatus(this.status);
        assignment.setNumber(this.number);
        assignment.setGithubUrl(this.githubUrl);
        assignment.setBranch(this.branch);
        assignment.setReviewVideoUrl(this.codereviewVideoUrl);
        assignment.setUserId(this.userId);
        assignment.setCodeReviewerId(this.codeReviewerId); // If CodeReviewer is a User object, replace with setCodeReviewer(codeReviewerObject)

        return assignment;
    }

    @Override
    public String toString() {
        return "PutAssignmentRequest{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", number=" + number +
                ", githubUrl='" + githubUrl + '\'' +
                ", branch='" + branch + '\'' +
                ", reviewVideoUrl='" + codereviewVideoUrl + '\'' +
                ", user=" + userId +
                ", codeReviewer=" + codeReviewerId +
                '}';
    }
}
