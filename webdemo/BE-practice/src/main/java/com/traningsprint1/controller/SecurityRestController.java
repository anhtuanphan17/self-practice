package com.traningsprint1.controller;

import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.response.JwtResponse;
import com.traningsprint1.security.JwtUtility;
import com.traningsprint1.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecurityRestController {

    @Autowired
    private ISecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        return new ResponseEntity<>(securityService.loginByAccount(loginRequest), HttpStatus.OK);
    }

}
