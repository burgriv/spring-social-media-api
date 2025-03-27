package com.example.controller;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.*;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidCredentialsException;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) throws AuthenticationException, InvalidCredentialsException {
        return ResponseEntity.status(200).body(accountService.createAccount(account));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(Exception ex) {return ex.getMessage();}

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRegistration(Exception ex) {return ex.getMessage();}

}
