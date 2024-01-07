package com.saurabh.controller;

import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.models.Story;
import com.saurabh.models.User;
import com.saurabh.service.StoryService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Story newStory = storyService.createStory(story, user);
            return new ResponseEntity<>(newStory, HttpStatus.CREATED);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Story>> findStoriesOfUser(@PathVariable Integer userId) throws Exception {
        List<Story> userStories = storyService.findStoryByUserId(userId);
        return new ResponseEntity<>(userStories, HttpStatus.OK);
    }
}
