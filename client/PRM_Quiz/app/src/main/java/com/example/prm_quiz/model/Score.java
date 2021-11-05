package com.example.prm_quiz.model;

import java.sql.Date;

public class Score {
    private Long id;
    private Long userId;
    private Long quizId;
    private String quizName;
    private int numOfCorrectQuestion;
    private int size;
    private String date;

    public Score() {
    }

    public Score(Long id, Long userId, Long quizId, String quizName, int numOfCorrectQuestion, int size, String date) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.quizName = quizName;
        this.numOfCorrectQuestion = numOfCorrectQuestion;
        this.size = size;
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

    public int getNumOfCorrectQuestion() {
        return numOfCorrectQuestion;
    }

    public void setNumOfCorrectQuestion(int numOfCorrectQuestion) {
        this.numOfCorrectQuestion = numOfCorrectQuestion;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
