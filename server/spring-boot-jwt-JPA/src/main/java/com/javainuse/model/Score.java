package com.javainuse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_score3")
public class Score {
	private Long id;
	private Long userId;
	private Long quizId;
	private int score;
	public Score() {
		
	}
	
	public Score(Long id, Long user_id, Long quiz_id, int score) {
		super();
		this.id = id;
		this.userId = user_id;
		this.quizId = quiz_id;
		this.score = score;
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
	public Long getUser_id() {
		return userId;
	}

	public void setUser_id(Long user_id) {
		this.userId = user_id;
	}
	@Column(name = "quiz_id", nullable = false)
	public Long getQuiz_id() {
		return quizId;
	}

	public void setQuiz_id(Long quiz_id) {
		this.quizId = quiz_id;
	}

	@Column(name = "Score", nullable = false)
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
	
}
