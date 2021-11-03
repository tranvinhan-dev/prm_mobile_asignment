/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javainuse.controller;

import com.javainuse.model.Question;
import com.javainuse.model.Quiz;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import question.ResourceNotFoundException;
import com.javainuse.dao.QuestionRepository;
import com.javainuse.dao.QuizRepository;

/**
 *
 * @author TranViNhan
 */
@RestController
@RequestMapping("/api/v1")
public class QuestionController {

	private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    @CrossOrigin
    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") Long questionId)
            throws ResourceNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + questionId));
        return ResponseEntity.ok().body(question);
    }

    @CrossOrigin
    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
//        return questionRepository.save(question);
    	Optional<Quiz> optionalQuiz = quizRepository.findById(question.getQuiz().getId());
        if (!optionalQuiz.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        question.setQuiz(optionalQuiz.get());

        Question savedQuestion = questionRepository.save(question);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedQuestion.getId()).toUri();

        return ResponseEntity.created(location).body(savedQuestion);
    }

    @CrossOrigin
    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable(value = "id") Long questionId,
            @Valid @RequestBody Question questionDetails) throws ResourceNotFoundException {
//        Question question = questionRepository.findById(questionId)
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + questionId));
//        question = new Question(questionDetails.getQuestion(),questionDetails.getAnswerA(),questionDetails.getAnswerB(),questionDetails.getAnswerC(),questionDetails.getAnswerD(),questionDetails.getCorrectAnswer(),questionDetails.getQuiz().getId());
//        final Question updatedQuestion = questionRepository.save(question);
//        return ResponseEntity.ok(updatedQuestion);
    	Optional<Quiz> optionalQuiz = quizRepository.findById(questionDetails.getQuiz().getId());
        if (!optionalQuiz.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (!optionalQuestion.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        questionDetails.setQuiz(optionalQuiz.get());
        questionDetails.setId(optionalQuestion.get().getId());
        questionRepository.save(questionDetails);

        return ResponseEntity.noContent().build();
    	
    }

    @CrossOrigin
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable(value = "id") Long questionId)
            throws ResourceNotFoundException {
//        Question question = questionRepository.findById(questionId)
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + questionId));
//
//        questionRepository.delete(question);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
    	 Optional<Question> optionalQuestion = questionRepository.findById(questionId);
         if (!optionalQuestion.isPresent()) {
             return ResponseEntity.unprocessableEntity().build();
         }

         questionRepository.delete(optionalQuestion.get());

         return ResponseEntity.noContent().build();
    	
    }
}
