package com.traningsprint1.service;

import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.response.JwtResponse;
/** ISecurityService
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
public interface ISecurityService {
    JwtResponse loginByAccount(LoginRequest loginRequest);
}
