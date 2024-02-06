package com.example.service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import com.example.entity.Message;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
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

    public List<Message> getAllMessagesByUser(Integer id){
        return messageRepository.findByPostedBy(id);
    }

    public Message createNewMessage(Message message){
        Boolean exists = accountRepository.existsById(message.getPosted_by());
        if((message.getMessage_text().length() == 0) || (message.getMessage_text().length() > 255) || !exists){
            return null;
        }
        return messageRepository.save(message);
    }

    public Message updateMessage(Integer message_id, Message message){
        Optional<Message> newMessage = messageRepository.findById(message_id);
        if(newMessage.isPresent()){
            if(message.getMessage_text().length() > 0 && message.getMessage_text().length() <= 255){
                Message finMessage = newMessage.get();
                finMessage.setMessage_text(message.getMessage_text());
                return finMessage;
            } 
        }
        return null;
    }

    public int deleteMessageById(Integer id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
