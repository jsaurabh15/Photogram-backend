package com.saurabh.service;

import com.saurabh.exceptions.ChatNotFoundException;
import com.saurabh.models.Chat;
import com.saurabh.models.User;

import java.util.List;

public interface ChatService {

    public Chat createChat(User firstUser, User secondUser);
    public Chat findChatById(Integer chatId) throws ChatNotFoundException;
    public List<Chat> findChatOfUser(Integer userId);
}
