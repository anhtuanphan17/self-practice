package com.traningsprint1.controller;

import com.traningsprint1.models.Account;
import com.traningsprint1.payload.request.CheckVerificationCodeRequest;
import com.traningsprint1.payload.request.LoginRequest;
import com.traningsprint1.payload.request.ResetPasswordRequest;
import com.traningsprint1.payload.response.JwtResponse;
import com.traningsprint1.service.IAccountService;
import com.traningsprint1.service.ISecurityService;
import com.traningsprint1.service.impl.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;


/** SecurityRestController is the class used for receiving request and sending respond data which is relating to security as login function,...
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecurityRestController {

    @Autowired
    IAccountService accountService;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This authenticateUser function is to authenticate login request from client
     * @param  loginRequest
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(securityService.loginByAccount(loginRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/find-by-email")
    public ResponseEntity<Account> findByUserName(@RequestBody String email) throws MessagingException, UnsupportedEncodingException {
        Account account = accountService.getAccountByEmail(email);
        if (account != null) {
            String code = accountService.setVerificationCode(account);
            emailSendService.sendEmail(account.getEmail(), account.getCustomer().getCustomerName(), code);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/check-verificationCode")
    public ResponseEntity<Boolean> checkVerificationCode(@RequestBody CheckVerificationCodeRequest checkVerificationCodeRequest){
        Account account = accountService.getAccountByEmail(checkVerificationCodeRequest.getEmail());
        if (account != null && account.getVerificationCode().equalsIgnoreCase(checkVerificationCodeRequest.getCode())){
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        Account account = this.accountService.getAccountByEmail(resetPasswordRequest.getEmail());
        if(account != null & resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword())){
        String encryptedPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
        this.accountService.changePassword(encryptedPassword,account.getId());
        return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
