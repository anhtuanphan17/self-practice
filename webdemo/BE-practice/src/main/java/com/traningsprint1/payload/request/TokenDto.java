package com.traningsprint1.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * this TokenDto class is the object considered as a login request of client when client login by google account
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String token;

}
