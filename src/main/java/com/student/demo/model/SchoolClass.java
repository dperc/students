package com.student.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class SchoolClass {

    @Id
    @GeneratedValue
    private Long id;

    private String schoolClassName;

    private String schoolClassDescription;

    public SchoolClass() {
    }

    public SchoolClass(Long id, String schoolClassName, String schoolClassDescription) {
        this.id = id;
        this.schoolClassName = schoolClassName;
        this.schoolClassDescription = schoolClassDescription;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolClassName() {
        return schoolClassName;
    }

    public void setSchoolClassName(String schoolClassName) {
        this.schoolClassName = schoolClassName;
    }

    public String getSchoolClassDescription() {
        return schoolClassDescription;
    }

    public void setSchoolClassDescription(String schoolClassDescription) {
        this.schoolClassDescription = schoolClassDescription;
    }
}
