package com.traningsprint1.service.impl;

import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.response.JwtResponse;
import com.traningsprint1.security.JwtUtility;
import com.traningsprint1.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Service
public class SecurityServiceImpl implements ISecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtility jwtUtility;

    /**
     * This authenticateUser function is to authenticate login request from client
     * @param  loginRequest
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    public JwtResponse loginByAccount(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtility.generateJwtToken(loginRequest.getUsername());

        AccountDetailsImpl accountDetails = (AccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = accountDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new JwtResponse(jwt, accountDetails.getId(), accountDetails.getUsername(), accountDetails.getImageLink(), roles);
    }

}
