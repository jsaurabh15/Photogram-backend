package com.saurabh.service.impl;

import com.saurabh.models.Chat;
import com.saurabh.models.Message;
import com.saurabh.models.User;
import com.saurabh.repository.ChatRepository;
import com.saurabh.repository.MessageRepository;
import com.saurabh.service.ChatService;
import com.saurabh.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message createMessage(User user, Integer chatId, Message message) {
        Chat chat = chatService.findChatById(chatId);

        Message newMessage = new Message();

        newMessage.setChat(chat);
        newMessage.setContent(message.getContent());
        newMessage.setImage(message.getImage());
        newMessage.setUser(user);
        newMessage.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(newMessage);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        return  savedMessage;
    }

    @Override
    public List<Message> findChatsMessages(Integer chatId) {
        Chat chat = chatService.findChatById(chatId);
        return messageRepository.findByChatId(chatId);
    }
}
