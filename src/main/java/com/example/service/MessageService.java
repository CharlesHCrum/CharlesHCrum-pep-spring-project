package com.example.service;

import com.example.repository.MessageRepository;

import com.example.entity.Message;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message getMessageById(Integer id){
        Optional<Message> newMessage = messageRepository.findById(id);
        if(newMessage.isPresent()){
            return newMessage.get();
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public int deleteMessageById(Integer id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
