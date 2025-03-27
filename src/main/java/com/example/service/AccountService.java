package com.example.service;

import com.example.repository.AccountRepository;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidCredentialsException;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) throws AuthenticationException, InvalidCredentialsException {
        if (account.getUsername().length() <= 0) throw new InvalidCredentialsException();
        if (account.getPassword().length() < 4) throw new InvalidCredentialsException();
        if (accountRepository.existsByUsername(account.getUsername())) throw new AuthenticationException();
        return accountRepository.save(account);
    }
}
