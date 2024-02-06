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
        this.accountRepository = accountRepository; //so we can use findByID to make sure account exists to create message
    }

    /*
     * Retrieve a message by ID only if message exist 
     * @param message id from endpoint
     * @return Message object retrieved
     */
    public Message getMessageById(Integer id){
        Optional<Message> newMessage = messageRepository.findById(id);
        if(newMessage.isPresent()){
            return newMessage.get();
        }
        return null;
    }

    /*
     * Gets a list of all messages by all users using built in jra query
     * @return a list of messages
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /*
     * Get all messages from a particular user based on id 
     * Uses custom query from MessageRepository
     * @param account id from endpoint
     * @return list of messages 
     */
    public List<Message> getAllMessagesByUser(Integer id){
        return messageRepository.findByPostedBy(id);
    }

    /*
     * Creates a new message if account which is posting exists, and if text fits requirements
     * @param message object with info except message id 
     * @return message persisted to database
     */
    public Message createNewMessage(Message message){
        Boolean exists = accountRepository.existsById(message.getPosted_by());
        if((message.getMessage_text().length() == 0) || (message.getMessage_text().length() > 255) || !exists){
            return null;
        }
        return messageRepository.save(message);
    }

    /*
     * Updates an already existing message
     * Only updates message if message already exists and message text meets requirements
     * @param message_id from endpoint, and message object that contains the text
     * @return updated message
     */
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

   /*
    * Delete message via ID. only deletes if message already exists.
    * @param message id from endpoint for message to be deleted
    * @return 1 if message actually deleted (1 row updated), 0 if no message found
    */
    public int deleteMessageById(Integer id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
