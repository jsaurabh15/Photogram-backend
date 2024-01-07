package com.saurabh.controller;

import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.models.User;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/get/{userId}")
    public User getUserById(@PathVariable  Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        return user;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user, @RequestHeader("Authorization") String jwt) throws Exception {
           try {
               User loggedInUser = userService.findUserFromAuthToken(jwt);
               User updatedUser = userService.updateUser(user, loggedInUser.getId());
               return new ResponseEntity<>(updatedUser, HttpStatus.OK);
           } catch (EmailNotFoundException e) {
               return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           }
    }

    @PutMapping("/follow/{personId}")
    public ResponseEntity<?> followUser(@RequestHeader("Authorization") String jwt, @PathVariable Integer personId) throws Exception {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            User currentUser = userService.followUser(user.getId(), personId);
            return new ResponseEntity<>(currentUser,HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/unfollow/{personId}")
    public ResponseEntity<?> unfollowUser(@RequestHeader("Authorization") String jwt, @PathVariable Integer personId) throws Exception {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            User currentUser = userService.unfollowUser(user.getId(), personId);
            return new ResponseEntity<>(currentUser,HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String query) {
        List<User> users = userService.searchUser(query);
        return users;
    }
    @GetMapping("/all-users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserFromToken(@RequestHeader("Authorization") String jwt) {
        try {
           User user = userService.findUserFromAuthToken(jwt);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
