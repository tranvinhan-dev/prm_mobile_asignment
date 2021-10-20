package com.example.prm_quiz.model;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    private String name;
    private String username;
    private String role;

    public RegisterResponse(String name, String username, String role) {
        this.name = name;
        this.username = username;
        this.role = role;
    }

    public RegisterResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
