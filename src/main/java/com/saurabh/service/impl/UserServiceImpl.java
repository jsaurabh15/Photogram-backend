package com.saurabh.service.impl;

import com.saurabh.exceptions.EmailNotFoundException;
import com.saurabh.exceptions.UserAlreadyExistsException;
import com.saurabh.models.User;
import com.saurabh.repository.UserRepository;
import com.saurabh.security.JwtTokenHelper;
import com.saurabh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Override
    public User registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws Exception {

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new Exception("user not exist with userid " +userId);
        }
        return user.get();
    }

    @Override
    public User findUserByEmail(String email)  {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new EmailNotFoundException("user not exist with email id: " +email);
        }
        return user.get();
    }

    @Override
    public User followUser(Integer userId, Integer personId) throws Exception {
        User user = findUserById(userId);
        User person = findUserById(personId);

        person.getFollowers().add(user.getId());
        user.getFollowings().add(person.getId());

        userRepository.save(user);
        userRepository.save(person);
        return user;
    }

    @Override
    public User unfollowUser(Integer userId, Integer personId) throws Exception {
        User user = findUserById(userId);
        User person = findUserById(personId);

        person.getFollowers().remove(user.getId());
        user.getFollowings().remove(person.getId());

        userRepository.save(user);
        userRepository.save(person);
        return user;
    }

    @Override
    public User findUserFromAuthToken(String jwt) throws Exception {
        String remainJwt = jwt.substring(7);
        String email = jwtTokenHelper.getUsernameFromToken(remainJwt);
        try {
            return findUserByEmail(email);
        } catch (EmailNotFoundException e) {
           throw new EmailNotFoundException("Email not found with id: " +email);
        } catch (Exception e) {
            throw new Exception("something went wrong..");
        }
    }

    @Override
    public User updateUser(User user,  Integer userId) throws Exception {
        Optional<User> existingUser = userRepository.findById(userId);
        if(existingUser.isEmpty()) {
            throw new Exception("user not exist with user id" +userId);
        }

        if(user.getFirstName() != null) {
            existingUser.get().setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null) {
            existingUser.get().setLastName(user.getLastName());
        }
       if(user.getPassword() != null) {
           existingUser.get().setPassword(user.getPassword());
       }
       if(user.getGender() != null) {
           existingUser.get().setGender(user.getGender());
       }

        return userRepository.save(existingUser.get());
    }

    @Override
    public List<User> searchUser(String query) {

        return userRepository.searchUser(query);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


}
