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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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

    @PostMapping("/google")
    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody TokenDto tokenDto) throws Exception {
        System.out.println("pass " + password);
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport, factory)
                        .setAudience(Collections.singleton(idClient));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(), tokenDto.getToken());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        email = payload.getEmail();
        Account account = new Account();
        if (accountService.ifEmailExist(email)) {
            account = accountService.getAccountByEmail(email);
        }
         else {
//            account = createUser(email);
        }
            LoginRequest jwtLogin = new LoginRequest();
            jwtLogin.setUsername(account.getEmail());
            jwtLogin.setPassword(password);

            JwtResponse jwtResponse = securityService.loginByAccount(jwtLogin);

            return new ResponseEntity<JwtResponse>(new JwtResponse(jwtResponse.getToken(), jwtResponse.getId(), jwtResponse.getUsername(), jwtResponse.getImageLink(), jwtResponse.getRoles()), HttpStatus.OK);
        }
    }

