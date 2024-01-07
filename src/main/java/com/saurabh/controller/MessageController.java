package com.saurabh.controller;

import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.models.Message;
import com.saurabh.models.User;
import com.saurabh.service.MessageService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/chat/{chatId}")
    public ResponseEntity<?> createMessage(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId, @RequestBody Message message) {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Message newMessage = messageService.createMessage(user, chatId, message);
            return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?> findChatsMessages(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId) {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            List<Message> chatMessages = messageService.findChatsMessages(chatId);
            return new ResponseEntity<>(chatMessages, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
