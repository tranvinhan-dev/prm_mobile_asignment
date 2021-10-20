/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javainuse.controller;

import com.javainuse.model.Subject;
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
import com.javainuse.dao.SubjectRepository;

/**
 *
 * @author TranViNhan
 */
@RestController
@RequestMapping("/api/v1")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @CrossOrigin
    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable(value = "id") Long subjectId)
            throws ResourceNotFoundException {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));
        return ResponseEntity.ok().body(subject);
    }

    @CrossOrigin
    @PostMapping("/subjects")
    public Subject createSubject(@Valid @RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    @CrossOrigin
    @PutMapping("/subjects/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable(value = "qid") Long subjectId,
            @Valid @RequestBody Subject subjectDetails) throws ResourceNotFoundException {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));
        subject.setName(subjectDetails.getName());
        subject.setStatus(subjectDetails.isStatus());
        final Subject updatedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(updatedSubject);
    }

    @CrossOrigin
    @DeleteMapping("/subjects/{id}")
    public Map<String, Boolean> deleteSubject(@PathVariable(value = "id") Long subjectId)
            throws ResourceNotFoundException {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));

        subjectRepository.delete(subject);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
