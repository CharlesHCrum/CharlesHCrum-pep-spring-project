package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController 
public class SocialMediaController {
    private MessageService messageService;
    private AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("/register") 
    public Account registerNewAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @GetMapping("/messages/{message_id}")
    public Message getMessage(@PathVariable Integer message_id){
        Message newMessage = messageService.getMessageById(message_id);
        return newMessage;
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        List<Message> list = messageService.getAllMessages();
        return list;
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer message_id) { 
        int result = messageService.deleteMessageById(message_id); 
        if(result == 1){
            return ResponseEntity.ok().body("1");
        }
        return ResponseEntity.ok().body("");
    }

}
