package com.saurabh.service.impl;

import com.saurabh.exceptions.ChatNotFoundException;
import com.saurabh.models.Chat;
import com.saurabh.models.User;
import com.saurabh.repository.ChatRepository;
import com.saurabh.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(User firstUser, User secondUser) {
        Chat isExist = chatRepository.findChatByUserId(firstUser, secondUser);

        if(isExist != null) {
            return isExist;
        }

        Chat chat = new Chat();
        chat.getUsers().add(firstUser);
        chat.getUsers().add(secondUser);
        chat.setTimestamp(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);

        if(chat.isEmpty()) {
            throw new ChatNotFoundException("chat does not exists with id  "+chatId);
        }
        return chat.get();
    }

    @Override
    public List<Chat> findChatOfUser(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }
}
