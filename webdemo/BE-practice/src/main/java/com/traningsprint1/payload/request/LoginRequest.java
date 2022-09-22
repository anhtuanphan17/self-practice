package com.traningsprint1.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * this LoginRequest class is the object considered as a login request of client
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;


}
