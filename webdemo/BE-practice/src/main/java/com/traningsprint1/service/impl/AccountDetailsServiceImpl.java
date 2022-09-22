package com.traningsprint1.service.impl;

import com.traningsprint1.models.Account;
import com.traningsprint1.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Class:class AccountDetailsServiceImpl implements interface UserDetailsService to override method
 loadUserByUsername(String username), which is used to authenticate account information in database with login request later
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IAccountRepository iAccountRepository;

    /** Function: This loadUserByUsername() method will return a UserDetails object with the parameter username.
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = iAccountRepository.findByUserName(username);
        if (account == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        return AccountDetailsImpl.build(account);
    }
}
