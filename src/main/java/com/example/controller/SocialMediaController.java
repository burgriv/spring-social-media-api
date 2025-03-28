package com.example.controller;
import org.springframework.web.bind.annotation.RestController;
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
 * SocialMediaController sets up all handlers, utilizing AccountService and MessageService to delegate responsibilities.
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


    /**
     * Registers a new account.
     * @param account The account to add to the database; from the request body.
     * @return The account persisted to the database.
     * @throws AuthenticationException
     * @throws InvalidDataException
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) throws AuthenticationException, InvalidDataException {
        return ResponseEntity.status(200).body(accountService.register(account));
    }

    /**
     * Logs an account in.
     * @param account The account to login; from the request body.
     * @return The account logged in.
     * @throws UnauthorizedException
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws UnauthorizedException {
        return ResponseEntity.status(200).body(accountService.login(account));
    }

    /**
     * Adds a message to the database.
     * @param message The message to add to the database.
     * @return The message persisted to the database.
     * @throws InvalidDataException
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) throws InvalidDataException {
        return ResponseEntity.status(200).body(messageService.create(message));
    }

    /**
     * Gets all messages from the database.
     * @return The List of all Message objects.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    /**
     * Gets the message specified by message_id
     * @param message_id The message to fetch from the database.
     * @return The Message fetched.
     * @throws InvalidDataException
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    /**
     * Deletes a message specified by message_id
     * @param message_id The message to delete.
     * @return 1 if successful, null otherwise.
     * @throws InvalidDataException
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(message_id));
    }

    /**
     * Updates a message specified by message_id with message_text.
     * @param message_id Corresponds to the message to update.
     * @param message_text The message_text to update message_id with.
     * @return 1 if successful, null otherwise.
     * @throws InvalidDataException
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message) throws InvalidDataException {
        return ResponseEntity.status(200).body(messageService.updateMessageById(message_id, message.getMessageText()));
    }

    /**
     * Gets all message from user specificed by account_id
     * @param account_id The account_id of the Account from which to fetch messages.
     * @return 1 if successful, null otherwise.
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessageByUser(@PathVariable int account_id) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUser(account_id));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(Exception ex) {return ex.getMessage();}

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRegistration(Exception ex) {return ex.getMessage();}

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(Exception ex) {return ex.getMessage();}
}
