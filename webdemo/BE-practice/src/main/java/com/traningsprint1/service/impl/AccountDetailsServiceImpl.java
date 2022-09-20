package com.traningsprint1.service.impl;

import com.traningsprint1.models.Account;
import com.traningsprint1.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IAccountRepository iAccountRepository;

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
