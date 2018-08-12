package com.student.demo.services.impl;

import com.student.demo.model.SchoolClass;
import com.student.demo.model.Student;
import com.student.demo.services.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcRepository implements DbService {

    Logger logger = LoggerFactory.getLogger(JdbcRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<SchoolClass> getAllSchoolClasses() {
        return jdbcTemplate.query("select * from schoolclass", new BeanPropertyRowMapper<>(SchoolClass.class));
    }

    @Override
    public List<SchoolClass> getSchoolClassByName(String schoolClassName) {
        List schoolClassList = jdbcTemplate.query("select * from schoolclass where schoolclassname=?",
                new Object[] { schoolClassName }, new BeanPropertyRowMapper<>(SchoolClass.class));

        if(schoolClassList.size() == 0) {
            logger.info("No classes found.");
            return null;

        } else {
            return schoolClassList;
        }
    }

    @Override
    public Boolean enrollStudentToClasses(Long studentId, List<Long> schoolClasses) {
        boolean status = true;

        for(Long schoolClassId : schoolClasses) {

            List queryResult = jdbcTemplate.queryForList("select * from classenrolments where schoolclassid=? and studentId=?",
                    new Object[] { schoolClassId, studentId });

            // enroll student only if he is not already enrolled to a class
            if(queryResult.size() == 0) {
                jdbcTemplate.update("insert into classenrolments(schoolClassId, studentId) values(? ,?)",
                        new Object[]{schoolClassId, studentId});

            } else {
                logger.warn("Student already enrolled to at least one of the classes.");
                status = false;
            }
        }

        return status;
    }

    @Override
    public Boolean cancelStudentEnrollment(Long studentId, Long schoolClassId) {
        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList("select * from classenrolments where schoolclassid=? and studentId=?",
                new Object[] { schoolClassId, studentId });

        // student was enrolled to the class
        if(queryResult.size() == 1) {
            jdbcTemplate.update("delete from classenrolments where id=?",
                    new Object[]{queryResult.get(0).get("ID")});
            return true;

        } else if(queryResult.size() == 0) {
            logger.warn("Student not enrolled to this class.");
            return false;

        } else {
            logger.error("This should not happen!");
            return false;
        }
    }

    @Override
    public String getStudentHash(Long studentId) {
        List<Student> studentList = jdbcTemplate.query("select * from student where id=?",
                new Object[] { studentId }, new BeanPropertyRowMapper<>(Student.class));

        if(studentList.size() == 0) {
            logger.info("User not found.");
            return null;

        } else {
            return studentList.get(0).getAuthHash();
        }
    }
}
