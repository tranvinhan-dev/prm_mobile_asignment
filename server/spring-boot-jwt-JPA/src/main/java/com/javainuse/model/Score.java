package com.javainuse.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_score")
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
		super();
		this.id = id;
		this.userId = userId;
		this.quizId = quizId;
		this.quizName = quizName;
		this.score = score;
		this.date = date;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}


	@Column(name = "quiz_id", nullable = false)
	public Long getQuizId() {
		return quizId;
	}



	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}


	@Column(name = "quiz_name", nullable = false)
	public String getQuizName() {
		return quizName;
	}



	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	

	@Column(name = "Score", nullable = false)
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}



	@Column(name = "date", nullable = false)
	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
