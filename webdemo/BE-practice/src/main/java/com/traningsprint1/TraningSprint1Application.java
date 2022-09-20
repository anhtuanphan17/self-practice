package com.traningsprint1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TraningSprint1Application {

    public static void main(String[] args) {
        SpringApplication.run(TraningSprint1Application.class, args);
//
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String a = bCryptPasswordEncoder.encode("Tuan123");
//        System.out.println(a);
    }

}
