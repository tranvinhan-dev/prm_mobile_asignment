/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javainuse.controller;

import com.javainuse.model.Question;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import question.ResourceNotFoundException;
import com.javainuse.dao.QuestionRepository;

/**
 *
 * @author TranViNhan
 */
@RestController
@RequestMapping("/api/v1")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

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
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionRepository.save(question);
    }

    @CrossOrigin
    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable(value = "qid") Long questionId,
            @Valid @RequestBody Question questionDetails) throws ResourceNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + questionId));
        question = new Question(questionDetails.getQuestion(),questionDetails.getAnswerA(),questionDetails.getAnswerB(),questionDetails.getAnswerC(),questionDetails.getAnswerD(),questionDetails.getCorrectAnswer());
        final Question updatedQuestion = questionRepository.save(question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @CrossOrigin
    @DeleteMapping("/questions/{id}")
    public Map<String, Boolean> deleteQuestion(@PathVariable(value = "id") Long questionId)
            throws ResourceNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + questionId));

        questionRepository.delete(question);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
