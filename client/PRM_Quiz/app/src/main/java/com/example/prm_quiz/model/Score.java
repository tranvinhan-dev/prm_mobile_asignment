package com.example.prm_quiz.model;

import java.sql.Date;

public class Score {
    private Long id;
    private Long userId;
    private Long quizId;
    private String quizName;
    private int score;
    private String date;
    public Score() {
    }

    public Score(Long id, Long userId, Long quizId, String quizName, int score, String date) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.quizName = quizName;
        this.score = score;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
