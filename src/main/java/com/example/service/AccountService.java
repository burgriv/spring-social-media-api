package com.example.service;

import com.example.repository.AccountRepository;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidDataException;
import com.example.exception.UnauthorizedException;

/**
 * AccountService handles communication between SocialMediaController and AccountRepository.
 */
@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Communicates with an AccountRepository to register a new user.
     * @param account The account to register.
     * @return The account persisted to the database.
     * @throws AuthenticationException thrown if username already exists.
     * @throws InvalidDataException thrown if username or password are invalid.
     */
    public Account register(Account account) throws AuthenticationException, InvalidDataException {
        if (account.getUsername().length() <= 0) throw new InvalidDataException();
        if (account.getPassword().length() < 4) throw new InvalidDataException();
        if (accountRepository.existsByUsername(account.getUsername())) throw new AuthenticationException();
        return accountRepository.save(account);
    }

    /**
     * Communicates with an AccountRepository to login a user.
     * @param account The account to login.
     * @return The account persisted to the database.
     * @throws UnauthorizedException thrown if credentials are not authorized.
     */
    public Account login(Account account) throws UnauthorizedException {
        if (!accountRepository.existsByUsernameAndPassword(account.getUsername(), account.getPassword())) throw new UnauthorizedException();
        return accountRepository.findByUsername(account.getUsername());
    }
}
