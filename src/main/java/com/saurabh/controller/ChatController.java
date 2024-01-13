package com.saurabh.controller;

import com.saurabh.exceptions.ChatNotFoundException;
import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.models.Chat;
import com.saurabh.models.User;
import com.saurabh.request.ChatRequest;
import com.saurabh.service.ChatService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createChat(@RequestHeader("Authorization") String jwt, @RequestBody ChatRequest request) {
        try {
            User firstUser = userService.findUserFromAuthToken(jwt);
            User secondUser = userService.findUserById(request.getUserId());
            Chat chat = chatService.createChat(firstUser, secondUser);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/{chatId}")
    public ResponseEntity<?> findChatById(@PathVariable Integer chatId) {
       try {
           Chat chat = chatService.findChatById(chatId);
           return new ResponseEntity<>(chat, HttpStatus.OK);
       }
       catch (ChatNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }

    @GetMapping("/user")
    public ResponseEntity<?> findChatOfUser(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            List<Chat> chat = chatService.findChatOfUser(user.getId());
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
