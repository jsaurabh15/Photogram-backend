package com.saurabh.service;

import com.saurabh.models.Reels;
import com.saurabh.models.User;

import java.util.List;

public interface ReelsService {

    Reels createReel(Reels reel, User user);
    List<Reels> findAllReels();
    List<Reels> findReelsOfUser(Integer userId) throws Exception;

}
