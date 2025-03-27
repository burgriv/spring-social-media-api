package com.example.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.*;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.*;

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
    public ResponseEntity<Account> register(@RequestBody Account account) throws AuthenticationException, InvalidCredentialsException {
        return ResponseEntity.status(200).body(accountService.register(account));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws UnauthorizedException {
        return ResponseEntity.status(200).body(accountService.login(account));
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) throws InvalidCredentialsException {
        return ResponseEntity.status(200).body(messageService.create(message));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    //TODO
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(message_id));
    }

    // TODO
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        return ResponseEntity.status(200).body(messageService.updateMessageById(message_id, message.getMessageText()));
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessageByUser(@PathVariable int account_id) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUser(account_id));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(Exception ex) {return ex.getMessage();}

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRegistration(Exception ex) {return ex.getMessage();}

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(Exception ex) {return ex.getMessage();}
}
