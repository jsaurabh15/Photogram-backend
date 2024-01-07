package com.saurabh.service.impl;

import com.saurabh.models.Story;
import com.saurabh.models.User;
import com.saurabh.repository.StoryRepository;
import com.saurabh.service.StoryService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Story createStory(Story story, User user) {
        Story newStory = new Story();
        newStory.setCaptions(story.getCaptions());
        newStory.setUser(user);
        newStory.setImage(story.getImage());
        newStory.setTimestamp(LocalDateTime.now());
        return storyRepository.save(newStory) ;
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        return storyRepository.findByUserId(userId);
    }
}
