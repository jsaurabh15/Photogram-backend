package com.saurabh.controller;

import com.saurabh.exceptions.CommentNotFoundException;
import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.models.Comment;
import com.saurabh.models.User;
import com.saurabh.request.CommentRequest;
import com.saurabh.service.CommentService;
import com.saurabh.service.PostService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @PostMapping("/add/post/{postId}")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest, @PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Comment createdComment = commentService.createComment(commentRequest,postId, user.getId());
            return new ResponseEntity<>(createdComment, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<?> likeComment(@PathVariable Integer commentId, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserFromAuthToken(jwt);
            Comment updatedComment = commentService.likeComment(commentId, user.getId());
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> findCommentById(@PathVariable Integer commentId) {
        try {
            Comment comment = commentService.findCommentById(commentId);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
