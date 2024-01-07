package com.saurabh.service.impl;

import com.saurabh.exceptions.CommentNotFoundException;
import com.saurabh.models.Comment;
import com.saurabh.models.Post;
import com.saurabh.models.User;
import com.saurabh.repository.CommentRepository;
import com.saurabh.repository.PostRepository;
import com.saurabh.request.CommentRequest;
import com.saurabh.service.CommentService;
import com.saurabh.service.PostService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public Comment createComment(CommentRequest comment, Integer postId, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        newComment.setUser(user);
        newComment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(newComment);

        post.getComments().add(savedComment);
        postRepository.save(post);
        return savedComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) {
            throw new CommentNotFoundException("comment not found with id: "+commentId);
        }
        return comment.get();
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) {
            throw new CommentNotFoundException("comment not found with id: "+commentId);
        }

        User user = userService.findUserById(userId);

        if(!comment.get().getLiked().contains(user)) {
            comment.get().getLiked().add(user);
        }
        else {
            comment.get().getLiked().remove(user);
        }
        return commentRepository.save(comment.get());
    }
}
