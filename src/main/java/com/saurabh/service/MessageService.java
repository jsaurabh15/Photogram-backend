package com.saurabh.service;

import com.saurabh.models.Message;
import com.saurabh.models.User;

import java.util.List;

public interface MessageService {

    public Message createMessage(User user, Integer chatId, Message message);
    public List<Message> findChatsMessages(Integer chatId);
}
