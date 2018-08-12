package com.student.demo.services;

import com.student.demo.model.SchoolClass;

import java.util.List;

public interface DbService {

    List<SchoolClass> getAllSchoolClasses();

    List<SchoolClass> getSchoolClassByName(String schoolClassName);

    Boolean enrollStudentToClasses(Long studentId, List<Long> schoolClasses);

    Boolean cancelStudentEnrollment(Long studentId, Long schoolClass);

    String getStudentHash(Long studentId);
}
