package com.javainuse.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.OneToMany;

@Entity
@Table(name = "tbl_quiz")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "qName", nullable = false)
	private String name;
	@Column(name = "qTeacherName", nullable = false)
	private String teacherName;
	@Column(name = "qSubject", nullable = false)
	private String subject;
	@Column(name = "qPassword", nullable = false)
	private String password;
	@Column(name = "qTime", nullable = false)
	private int time;
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<Question> listQuestion;

	public Quiz() {

	}

	public Quiz(Long id, String name, String teacherName, String subject, String password, int time,
			List<Question> listQuestion) {
		super();
		this.id = id;
		this.name = name;
		this.teacherName = teacherName;
		this.subject = subject;
		this.password = password;
		this.time = time;
		this.listQuestion = listQuestion;
	}

	public Quiz(String name, String teacherName, String subject, String password, int time,
			List<Question> listQuestion) {
		super();

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
		for (int i = 0; i < listQuestion.size(); i++) {
			listQuestion.get(i).setQuiz(this);
		}
	}

}
