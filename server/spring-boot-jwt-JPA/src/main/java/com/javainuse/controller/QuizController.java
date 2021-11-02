/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javainuse.controller;

import com.javainuse.model.Quiz;
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
import com.javainuse.dao.QuizRepository;

/**
 *
 * @author TranViNhan
 */
@RestController
@RequestMapping("/api/v1")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @CrossOrigin
    @GetMapping("/quizs")
    public List<Quiz> getAllQuizs() {
        return quizRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/quizs/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable(value = "id") Long quizId)
            throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for this id :: " + quizId));
        return ResponseEntity.ok().body(quiz);
    }

    @CrossOrigin
    @PostMapping("/quizs")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @CrossOrigin
    @PutMapping("/quizs/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable(value = "qid") Long quizId,
            @Valid @RequestBody Quiz quizDetails) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for this id :: " + quizId));
        quiz = new Quiz(quizDetails.getName(),quizDetails.getTeacherName(),quizDetails.getSubject(),quizDetails.getPassword(),quizDetails.getTime(),quizDetails.getListQuestion());
        final Quiz updatedQuiz = quizRepository.save(quiz);
        return ResponseEntity.ok(updatedQuiz);
    }

    @CrossOrigin
    @DeleteMapping("/quizs/{id}")
    public Map<String, Boolean> deleteQuiz(@PathVariable(value = "id") Long quizId)
            throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for this id :: " + quizId));

        quizRepository.delete(quiz);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
