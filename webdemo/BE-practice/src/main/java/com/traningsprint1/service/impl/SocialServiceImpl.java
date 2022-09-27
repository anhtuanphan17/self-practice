package com.traningsprint1.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.traningsprint1.models.Account;
import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.request.TokenDto;
import com.traningsprint1.service.IAccountService;
import com.traningsprint1.service.ISocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Service
public class SocialServiceImpl implements ISocialService {
    private String email;
    @Autowired
    private IAccountService accountService;

    @Value("${google.id}")
    private String idClient;

    @Value("${mySecret.password}")
    private String password;

    /**Function: loginWithGoogle is used to authenticate the access token from client and find out emailand set email, password to login request
     * @Param: tokenDto
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Override
    public LoginRequest loginWithGoogle(TokenDto tokenDto) throws Exception {

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
        } else {
//            account = createUser(email);
        }
        LoginRequest jwtLogin = new LoginRequest();
        jwtLogin.setUsername(account.getEmail());
        jwtLogin.setPassword(password);


        return jwtLogin;
    }
}
