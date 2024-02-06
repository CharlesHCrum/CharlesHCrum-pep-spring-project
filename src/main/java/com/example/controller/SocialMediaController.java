package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    /*
     * Registers new account, status 409 if unsuccessful due to duplicate name, 400 if otherwise unsuccessful
     *  @param - request body from endpoint
     * @return - Response entity, blank if failed and the new account if successful
     */ 
    @PostMapping("/register") 
    public ResponseEntity<Account> registerNewAccount(@RequestBody Account account) {
        //check if password or username doesn't fit requirements. this avoids having to create a new repository object
        //to check by username in the controller class, and allows to service class to return the account
        if( (account.getPassword().length() < 4) || (account.getUsername().length() == 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Account newAccount = accountService.registerAccount(account);
        if (newAccount == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        else{
            return ResponseEntity.ok().body(newAccount); 
        }
    }

    /*
     * Login account
     *  401 if login unsuccessful 
     * @return logged in account if successful
     */
    @PostMapping("/login") 
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account newAccount = accountService.loginAccount(account);
        if (newAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else{
            return ResponseEntity.ok().body(newAccount); 
        }
    }

    /*
     * Retieves message based on given message id, blank if no message found
     *  @return message 
     */
    @GetMapping("/messages/{message_id}")
    public Message getMessage(@PathVariable Integer message_id){
        Message newMessage = messageService.getMessageById(message_id);
        return newMessage;
    }

    /*
     * Gets a list of all messages. Will be empty if no messages found
     * @return - list of messages
     */
    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        List<Message> list = messageService.getAllMessages();
        return list;
    }

    /*
     * Gets all messages from a particular user. Will be empty if no messages are found
     * @return list of messages
     */
    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getAllMessagesFromUser(@PathVariable Integer account_id){
        List<Message> list = messageService.getAllMessagesByUser(account_id);
        return list;
    }

    /*
     * Creates a new message. Status 400 if not successful 
     * @return - the created message if successful
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        Message newMessage = messageService.createNewMessage(message);
        if(newMessage == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(newMessage);       

        
    }
    
    /*
     * Updates an already existing message.
     * If message does not already exist, status 400 
     * @return updated message
     */
    @PatchMapping("/messages/{message_id}") 
    public ResponseEntity<?> updateMessage(@PathVariable Integer message_id, @RequestBody Message message) {
        Message newMessage = messageService.updateMessage(message_id, message);
        if (newMessage == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else{
            return ResponseEntity.ok().body(1); 
        }
    }

    /*
     * Deletes an already existing message        
     * Status 200 regardless of success
     * @return ResponseEntity with number of rows altered (at most one)
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer message_id) { 
        int result = messageService.deleteMessageById(message_id); 
        if(result == 1){
            return ResponseEntity.ok().body("1");
        }
        return ResponseEntity.ok().body("");
    }

}
