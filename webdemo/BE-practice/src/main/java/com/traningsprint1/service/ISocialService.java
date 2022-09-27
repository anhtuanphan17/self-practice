package com.traningsprint1.service;

import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.request.TokenDto;

/** ISocialService
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
public interface ISocialService {
    LoginRequest loginWithGoogle(TokenDto tokenDto) throws Exception;
}
