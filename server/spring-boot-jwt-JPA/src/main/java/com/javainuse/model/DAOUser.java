package com.javainuse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user")
public class DAOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column(columnDefinition = "varchar(255) default 'customer'")
    private String role;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private String name;
    
    public DAOUser() {
		super();
	}

	public DAOUser(long id, String username, String role, String password, String name) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
		this.password = password;
		this.name = name;
	}

	public DAOUser(String username, String role, String name) {
		super();
		this.username = username;
		this.role = role;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

}
