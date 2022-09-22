package com.traningsprint1.controller;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.traningsprint1.models.Account;
import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.request.TokenDto;
import com.traningsprint1.payload.response.JwtResponse;
import com.traningsprint1.service.IAccountService;
import com.traningsprint1.service.ISecurityService;
import com.traningsprint1.service.ISocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/** SocialRestController is the class use for receiving request and sending respond data relating to social login
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@RestController
@RequestMapping("/api/social")
@CrossOrigin("http://localhost:4200")
public class SocialRestController {

    private String email;

    @Autowired
    private IAccountService accountService;

    @Value("${google.id}")
    private String idClient;

    @Value("${mySecret.password}")
    private String password;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private ISocialService socialService;


    /**
     * This loginWithGoogle function is to authenticate login request from client by google authentication
     * @param  tokenDto
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @PostMapping("/google")
    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody TokenDto tokenDto) throws Exception {
            LoginRequest jwtLogin =  socialService.loginWithGoogle(tokenDto);
            JwtResponse jwtResponse = securityService.loginByAccount(jwtLogin);
            return new ResponseEntity<JwtResponse>(new JwtResponse(jwtResponse.getToken(), jwtResponse.getId(), jwtResponse.getUsername(), jwtResponse.getImageLink(), jwtResponse.getRoles()), HttpStatus.OK);
        }
    }

