package com.example.service;

import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
}