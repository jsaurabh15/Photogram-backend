package com.saurabh.service;

import com.saurabh.models.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);
    User findUserById(Integer userId) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User followUser(Integer userId,  Integer personId) throws Exception;
    User updateUser(User user, Integer userId) throws Exception;
    List<User> searchUser(String query);
    List<User> getUsers();
    User unfollowUser(Integer userId, Integer personId) throws Exception;

    User findUserFromAuthToken(String jwt) throws Exception;

}
