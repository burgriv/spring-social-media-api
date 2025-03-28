package com.example.service;

import com.example.entity.Message;
import com.example.exception.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

/**
 * MessageService handles communication between SocialMediaController, MessageRepository and AccountRepository.
 */
@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;
    private static final int ROWS_DELETED = 1;
    private static final int ROWS_UPDATED = 1;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Communicates with a MessageRepository to create a new message.
     * @param message The message to create.
     * @return The message persisted to the database.
     * @throws InvalidDataException thrown if user doesn't exist or message_text's size is invalid.
     */
    public Message create(Message message) throws InvalidDataException {
        if (message.getMessageText().length() <= 0
         || message.getMessageText().length() >= 255
         || !accountRepository.existsById(message.getPostedBy())) throw new InvalidDataException();
        
        return messageRepository.save(message);
    }

    /**
     * Communicates with a MessageRepository to get all messages.
     * @return The List of Messages retrieved from the database.
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * Communicates with a MessageRepository to get a message specified by message_id.
     * @param message_id Corresponds to the message to fetch.
     * @return The message retrieved from the database.
     */
    public Message getMessageById(int message_id){
        return messageRepository.findById(message_id).orElse(null);
    }

    /**
     * Communicates with a MessageRepository to delete a message specified by message_id.
     * @param message_id Corresponds to the message to delete.
     * @return ROWS_DELETED if successful, null otherwise
     */
    public Integer deleteMessageById(int message_id){
        if (!messageRepository.existsById(message_id)) return null;
        messageRepository.deleteById(message_id);
        return ROWS_DELETED;
    }

    /**
     * Communicates with a MessageRepository to fetch all messages from a specific user.
     * @param account_id Corresponds to the account from which to fetch all messages.
     * @return The List of Messages corresponding to the desired user.
     */
    public List<Message> getAllMessagesByUser(int account_id){
        return messageRepository.findAllByPostedBy(account_id);
    }

    /**
     * Communicates with a MessageRepository to update a message specified by message_id with message_text.
     * @param message_id Corresponds to the message to update.
     * @param message_text The text to use in the update.
     * @return ROWS_UPDATED if successful, null otherwise
     * @throws InvalidDataException thrown if the message doesn't exist or message_text is invalid.
     */
    public Integer updateMessageById(int message_id, String message_text) throws InvalidDataException {
        if (!messageRepository.existsById(message_id)
          || message_text.length() <= 0
          || message_text.length() > 255) throw new InvalidDataException();
        Message message = messageRepository.getById(message_id);
        message.setMessageText(message_text);
        messageRepository.save(message);
        return ROWS_UPDATED;
    }
}