package com.saurabh.controller;

import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.models.Reels;
import com.saurabh.models.User;
import com.saurabh.service.ReelsService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reels")
public class ReelsController {

    @Autowired
    private ReelsService reelsService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createReel(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Reels createdReel = reelsService.createReel(reel, user);
            return new ResponseEntity<>(createdReel, HttpStatus.CREATED);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Reels>> findAllReels() {
        List<Reels> reels = reelsService.findAllReels();
        return new ResponseEntity<>(reels, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reels>> findReelsOfUser(@PathVariable Integer userId) throws Exception {
        List<Reels> allReels = reelsService.findReelsOfUser(userId);
        return new ResponseEntity<>(allReels, HttpStatus.OK);
    }
}
