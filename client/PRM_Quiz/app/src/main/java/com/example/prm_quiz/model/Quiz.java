package com.example.prm_quiz.model;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {

    private Long id;
    private String name;
    private String teacherName;
    private String subject;
    private String password;
    private int time;
    private List<Question> listQuestion;

    public Quiz() {
    }

    public Quiz(Long id, String name, String teacherName, String subject, String password, int time, List<Question> listQuestion) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        this.subject = subject;
        this.password = password;
        this.time = time;
        this.listQuestion = listQuestion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Question> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<Question> listQuestion) {
        this.listQuestion = listQuestion;

    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", subject='" + subject + '\'' +
                ", password='" + password + '\'' +
                ", time=" + time +
                ", listQuestion=" + listQuestion +
                '}';
    }
}
