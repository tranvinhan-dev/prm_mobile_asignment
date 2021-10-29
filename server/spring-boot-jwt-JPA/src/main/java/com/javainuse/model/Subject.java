/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javainuse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author TranViNhan
 */
@Entity
@Table(name = "test_subject")
public class Subject{
    private long id;
    private String name;
    private boolean status;
    public Subject() {
    }

    public Subject(long id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "sName", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "sStatus", nullable = false)
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
