package com.example.prm_quiz.model;

public class DeleteResponse {
    private boolean deleted;

    public DeleteResponse() {
    }

    public DeleteResponse(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
