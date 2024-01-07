package com.saurabh.controller;

import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.exceptions.UnauthorizedDeleteException;
import com.saurabh.models.Post;
import com.saurabh.models.User;
import com.saurabh.response.ApiResponse;
import com.saurabh.service.PostService;
import com.saurabh.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/create/user")
    public ResponseEntity<?> createPost(@RequestBody Post post, @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Post newPost = postService.createNewPost(post, user.getId());
            return new ResponseEntity<>(newPost, HttpStatus.CREATED);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            String message = postService.deletePost(postId, user.getId());
            ApiResponse response = new ApiResponse(message, true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnauthorizedDeleteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> findPostByUserId(@PathVariable Integer userId) {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/all-posts")
    public ResponseEntity<List<Post>> findAllPost() {
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/save/{postId}")
    public ResponseEntity<?> savedPost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Post post = postService.savedPost(postId, user.getId());
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Post post = postService.likePost(postId, user.getId());
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
