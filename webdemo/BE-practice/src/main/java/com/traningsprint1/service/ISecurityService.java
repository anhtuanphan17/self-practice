package com.traningsprint1.service;

import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.response.JwtResponse;

public interface ISecurityService {
    JwtResponse loginByAccount(LoginRequest loginRequest);
}
