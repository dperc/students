package com.student.demo.services.impl;

import com.student.demo.services.DbService;
import com.student.demo.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    DbService dbService;

    @Override
    public boolean checkHash(HttpHeaders httpHeaders, Long studentId) {

        // Better option would be to create user session and use session token.

        String authHash = dbService.getStudentHash(studentId);

        if(authHash.equals(httpHeaders.get("authorization").get(0).toString())) {
            return true;
        } else {
            return false;
        }
    }
}
