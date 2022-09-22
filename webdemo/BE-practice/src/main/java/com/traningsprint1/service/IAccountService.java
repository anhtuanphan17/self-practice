package com.traningsprint1.service;

import com.traningsprint1.models.Account;

/** IAccountService
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
public interface IAccountService {
    Account findByUserName(String username);

    /** This ifEmailExist function is to check if has any account in database same email with param email
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    boolean ifEmailExist(String email);

    Account getAccountByEmail(String email);
}
