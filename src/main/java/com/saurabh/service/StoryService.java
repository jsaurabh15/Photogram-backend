package com.saurabh.service;

import com.saurabh.models.Story;
import com.saurabh.models.User;

import java.util.List;

public interface StoryService {

    Story createStory(Story story, User user);
    List<Story> findStoryByUserId(Integer userId) throws Exception;
}
