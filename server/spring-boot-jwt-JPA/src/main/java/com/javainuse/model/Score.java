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
	private int numOfCorrectQuestion;
	private int size;
	private String date;

	public Score() {

	}

	public Score(Long id, Long userId, Long quizId, String quizName, int numOfCorrectQuestion, int size, String date) {
		super();
		this.id = id;
		this.userId = userId;
		this.quizId = quizId;
		this.quizName = quizName;
		this.numOfCorrectQuestion = numOfCorrectQuestion;
		this.size = size;
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

	@Column(name = "num_correct_question", nullable = false)

	public int getNumOfCorrectQuestion() {
		return numOfCorrectQuestion;
	}

	public void setNumOfCorrectQuestion(int numOfCorrectQuestion) {
		this.numOfCorrectQuestion = numOfCorrectQuestion;
	}
	@Column(name = "size", nullable = false)
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Column(name = "date", nullable = false)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
