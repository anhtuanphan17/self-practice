package com.traningsprint1.service;

import com.traningsprint1.models.Account;

public interface IAccountService {
    Account findByUserName(String username);

    boolean ifEmailExist(String email);

    Account getAccountByEmail(String email);
}
