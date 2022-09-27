package com.traningsprint1.service.impl;

import com.traningsprint1.models.Account;
import com.traningsprint1.repository.IAccountRepository;
import com.traningsprint1.service.IAccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    IAccountRepository iAccountRepository;

    @Override
    public Account findByUserName(String username) {
        return this.iAccountRepository.findByUserName(username);
    }

    @Override
    public boolean ifEmailExist(String email) {
        return iAccountRepository.existsAccountByEmail(email);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return this.iAccountRepository.getAccountByEmail(email);
    }

    @Override
    public String setVerificationCode(Account account) {
        String code = RandomString.make(6);
        iAccountRepository.setVerification(account.getUserName(), code);
        return code;
    }

    @Override
    public void changePassword(String newPassword, Long id) {
        this.iAccountRepository.changePassword(newPassword,id);
    }
}
