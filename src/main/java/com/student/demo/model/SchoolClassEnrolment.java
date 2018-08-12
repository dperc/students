package com.student.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class SchoolClassEnrolment {

    @Id
    @GeneratedValue
    private Long id;

    private Long schoolClassName;

    private Long studentId;

    public SchoolClassEnrolment() {
    }

    public SchoolClassEnrolment(Long schoolClassName, Long studentId) {
        this.schoolClassName = schoolClassName;
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolClassName() {
        return schoolClassName;
    }

    public void setSchoolClassName(Long schoolClassName) {
        this.schoolClassName = schoolClassName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
