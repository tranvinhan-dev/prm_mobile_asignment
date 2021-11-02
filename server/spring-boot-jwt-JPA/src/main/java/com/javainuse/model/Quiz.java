package com.javainuse.model;

import java.util.List;

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
	 private Long id;
	    private String name;
	    private String teacherName;
	    private String subject;
	    private String password;
	    private int time;
		
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
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		@Column(name = "qName", nullable = false)
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Column(name = "qTeacherName", nullable = false)
		public String getTeacherName() {
			return teacherName;
		}
		public void setTeacherName(String teacherName) {
			this.teacherName = teacherName;
		}
		@Column(name = "qSubject", nullable = false)
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		@Column(name = "qPassword", nullable = false)
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		@Column(name = "qTime", nullable = false)
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
		@JsonManagedReference 
		@OneToMany(mappedBy = "quiz")
		public List<Question> getListQuestion() {
			return listQuestion;
		}
		public void setListQuestion(List<Question> listQuestion) {
			this.listQuestion = listQuestion;
		}
	    
}
