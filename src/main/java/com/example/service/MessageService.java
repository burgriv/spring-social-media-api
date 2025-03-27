package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message create(Message message) throws InvalidCredentialsException {
        if (message.getMessageText().length() <= 0
         || message.getMessageText().length() >= 255
         || !accountRepository.existsById(message.getPostedBy())) throw new InvalidCredentialsException();
        
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_id){
        return messageRepository.findById(message_id).orElse(null);
    }

    public Integer deleteMessageById(int message_id){
        Integer count = messageRepository.customDelete(message_id);
        if (count > 0) return count;
        return null;
    }

    public List<Message> getAllMessagesByUser(int account_id){
        return messageRepository.findAllByPostedBy(account_id);
    }

    public Integer updateMessageById(int message_id, String message_text){
        Integer count = messageRepository.customUpdate(message_text, message_id);
        if (count > 0) return count;
        return null;
    }
}