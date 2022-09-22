package com.traningsprint1.controller;

import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.response.JwtResponse;
import com.traningsprint1.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/** SecurityRestController is the class use for receiving request and sending respond data relating to security as login...
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecurityRestController {

    @Autowired
    private ISecurityService securityService;

    /**
     * This authenticateUser function is to authenticate login request from client
     * @param  loginRequest
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(securityService.loginByAccount(loginRequest), HttpStatus.OK);
    }



}
