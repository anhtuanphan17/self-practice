package com.traningsprint1.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * this JwtResponse class is the object considered as an object server response to client
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String imageLink;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String imageLink, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.imageLink = imageLink;
        this.roles = roles;
    }

}
