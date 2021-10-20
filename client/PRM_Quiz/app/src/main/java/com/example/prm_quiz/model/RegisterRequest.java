package com.example.prm_quiz.model;

import java.io.Serializable;

public class RegisterRequest  implements Serializable {
    private String username;
    private String password;
    private String name;

    public RegisterRequest(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
