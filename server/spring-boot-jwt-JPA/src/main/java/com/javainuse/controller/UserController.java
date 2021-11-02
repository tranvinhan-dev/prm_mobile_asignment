/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javainuse.controller;

import com.javainuse.model.DAOUser;
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

import com.javainuse.dao.UserDao;

/**
 *
 * @author TranViNhan
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserDao userRepository;

    @CrossOrigin
    @GetMapping("/users")
    public List<DAOUser> getAllDAOUsers() {
        return (List<DAOUser>) userRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/users/{id}")
    public ResponseEntity<DAOUser> getDAOUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        DAOUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("DAOUser not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }
    @CrossOrigin
    @GetMapping("/users/username/{username}")
    public ResponseEntity<DAOUser> getDAOUserByname(@PathVariable(value = "username") String username)
            throws ResourceNotFoundException {
        DAOUser user = userRepository.findByUsername(username);
        if(user==null) {
        	throw new ResourceNotFoundException("DAOUser not found for this username :: " + username);
        }
        return ResponseEntity.ok().body(user);
    }
    @CrossOrigin
    @PostMapping("/users")
    public DAOUser createDAOUser(@Valid @RequestBody DAOUser user) {
        return userRepository.save(user);
    }

    @CrossOrigin
    @PutMapping("/users/{id}")
    public ResponseEntity<DAOUser> updateDAOUser(@PathVariable(value = "qid") Long userId,
            @Valid @RequestBody DAOUser userDetails) throws ResourceNotFoundException {
        DAOUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("DAOUser not found for this id :: " + userId));
        user = new DAOUser(userDetails.getUsername(),userDetails.getRole(),userDetails.getName());
        final DAOUser updatedDAOUser = userRepository.save(user);
        return ResponseEntity.ok(updatedDAOUser);
    }

    @CrossOrigin
    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteDAOUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        DAOUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("DAOUser not found for this id :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
