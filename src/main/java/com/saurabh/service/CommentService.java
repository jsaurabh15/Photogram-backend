package com.saurabh.service;

import com.saurabh.models.Comment;
import com.saurabh.request.CommentRequest;

public interface CommentService {

    Comment createComment(CommentRequest comment, Integer postId, Integer userId) throws Exception;
    Comment findCommentById(Integer commentId);
    Comment likeComment(Integer commentId, Integer userId) throws Exception;

}
