package com.chaptoporg.service;

import org.springframework.stereotype.Service;
import com.chaptoporg.model.Message;
import com.chaptoporg.repo.MessageRepo;

@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }
}
