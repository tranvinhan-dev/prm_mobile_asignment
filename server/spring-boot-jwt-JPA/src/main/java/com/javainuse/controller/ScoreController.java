/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javainuse.controller;

import com.javainuse.model.Score;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.javainuse.dao.ScoreRepository;

/**
 *
 * @author TranViNhan
 */
@RestController
@RequestMapping("/api/v1")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;

    @CrossOrigin
    @GetMapping("/scores")
    public List<Score> getAllScores() {
        return scoreRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    @CrossOrigin
    @GetMapping("/scores/{id}")
    public ResponseEntity<Score> getScoreById(@PathVariable(value = "id") Long scoreId)
            throws ResourceNotFoundException {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found for this id :: " + scoreId));
        return ResponseEntity.ok().body(score);
    }

    @CrossOrigin
    @PostMapping("/scores")
    public Score createScore(@Valid @RequestBody Score score) {
        return scoreRepository.save(score);
    }

    @CrossOrigin
    @PutMapping("/scores/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable(value = "qid") Long scoreId,
            @Valid @RequestBody Score scoreDetails) throws ResourceNotFoundException {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found for this id :: " + scoreId));
        score = new Score();
        score.setNumOfCorrectQuestion(scoreDetails.getNumOfCorrectQuestion());
        score.setSize(scoreDetails.getSize());
        score.setQuizId(scoreDetails.getQuizId());
        score.setUserId(scoreDetails.getUserId());
        score.setQuizName(scoreDetails.getQuizName());
        score.setDate(scoreDetails.getDate());
        final Score updatedScore = scoreRepository.save(score);
        return ResponseEntity.ok(updatedScore);
    }

    @CrossOrigin
    @DeleteMapping("/scores/{id}")
    public Map<String, Boolean> deleteScore(@PathVariable(value = "id") Long scoreId)
            throws ResourceNotFoundException {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found for this id :: " + scoreId));

        scoreRepository.delete(score);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
