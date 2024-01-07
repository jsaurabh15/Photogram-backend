package com.saurabh.service.impl;

import com.saurabh.models.Reels;
import com.saurabh.models.User;
import com.saurabh.repository.ReelsRepository;
import com.saurabh.service.ReelsService;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsServiceImpl implements ReelsService {

    @Autowired
    private ReelsRepository reelsRepository;
    @Autowired
    private UserService userService;
    @Override
    public Reels createReel(Reels reel, User user) {
        Reels newReel = new Reels();
        newReel.setTitle(reel.getTitle());
        newReel.setUser(user);
        newReel.setVideo(reel.getVideo());
        return reelsRepository.save(newReel);
    }

    @Override
    public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findReelsOfUser(Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        return reelsRepository.findByUserId(userId);
    }
}
