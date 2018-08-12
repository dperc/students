package com.student.demo.controllers;

import com.student.demo.services.DbService;
import com.student.demo.model.RestResponse;
import com.student.demo.model.SchoolClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@PreAuthorize("@securityService.checkHash(#headers, #studentId)")
public class SchoolClassController {

    private final Logger logger = LoggerFactory.getLogger(SchoolClassController.class);

    @Autowired
    DbService dbService;

    /**
     *  Path returns all classes.
     *
     * @param headers
     * @param studentId
     * @return response entity with list of classes.
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getSchoolClasses(@RequestHeader HttpHeaders headers,
                                           @RequestParam("studentId") Long studentId) {
        logger.info("Got request to query all classes.");

        List<SchoolClass> schoolClassList= dbService.getAllSchoolClasses();

        RestResponse<List<SchoolClass>> restResponse = new RestResponse<>();
        restResponse.appendDataItem(schoolClassList);

        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    /**
     * Path returns class(es) which have the same name as is the value of parameter schoolClassName.
     *
     * @param headers
     * @param schoolClassName - class name search filter.
     * @param studentId
     * @return response entity with the list of found classes.
     */
    @RequestMapping(value = "/class", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getSchoolClassByName(@RequestHeader HttpHeaders headers,
                                               @RequestParam("className") String schoolClassName,
                                               @RequestParam("studentId") Long studentId) {
        logger.info("Got request to query class with name: {}", schoolClassName);

        // return list of classes because name is not necessarily unique
        List<SchoolClass> schoolClassList = dbService.getSchoolClassByName(schoolClassName);

        RestResponse<List<SchoolClass>> restResponse = new RestResponse<>();
        if(schoolClassList != null) {
            restResponse.appendDataItem(schoolClassList);
        } else {
            restResponse.setMessage("No classes found.");
        }

        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    /**
     * Enroll student to a class.
     * @param headers
     * @param schoolClassIds - class to which student is enrolled.
     * @param studentId - student that is enrolled.
     * @return response entity with flag if enrolment was successful and message.
     */
    @RequestMapping(value = "/enroll", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity enrollStudent(@RequestHeader HttpHeaders headers,
                                        @RequestParam("classId") List<Long> schoolClassIds,
                                        @RequestParam("studentId") Long studentId) {
        logger.info("Got request to enroll a student: {}, to a class: {}", studentId, schoolClassIds);

        boolean success = dbService.enrollStudentToClasses(studentId, schoolClassIds);

        RestResponse<Boolean> restResponse = new RestResponse<>();
        restResponse.appendDataItem(success);
        if(success) {
            restResponse.setMessage("Student successfully enrolled.");
        } else {
            restResponse.setMessage("Problems when enrolling student to a class, maybe student was already enrolled. Review enrollments.");
        }

        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }


    /**
     * Cancel student's enrolment to a class.
     * @param headers
     * @param schoolClassIds - class from which enrolment should be removed.
     * @param studentId - student of whom enrolment should be removed.
     * @return response entity with flag if enrolment removal was successful and message.
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity cancelStudentEnrolment(@RequestHeader HttpHeaders headers,
                                        @RequestParam("classId") Long schoolClassId,
                                        @RequestParam("studentId") Long studentId) {
        logger.info("Got request to cancel student: {} enrollment to a class: {}", studentId, schoolClassId);

        boolean success = dbService.cancelStudentEnrollment(studentId, schoolClassId);

        RestResponse<Boolean> restResponse = new RestResponse<>();
        restResponse.appendDataItem(success);
        if(success) {
            restResponse.setMessage("Enrollment successfully cancelled.");
        } else {
            restResponse.setMessage("Problem when cancelling enrollment. Maybe student was not enrolled to a class. Review studen enrollment.");
        }

        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }
}
