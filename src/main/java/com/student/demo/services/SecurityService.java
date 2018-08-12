package com.student.demo.services;

import org.springframework.http.HttpHeaders;

public interface SecurityService {

    boolean checkHash(HttpHeaders httpHeaders, Long studentId);
}
