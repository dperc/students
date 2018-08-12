package com.student.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String authHash;

    public Student() {
        super();
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(Long id, String name, String authHash) {
        this.id = id;
        this.name = name;
        this.authHash = authHash;
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

    public String getAuthHash() {
        return authHash;
    }

    public void setAuthHash(String authHash) {
        this.authHash = authHash;
    }
}

