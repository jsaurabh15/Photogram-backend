package com.saurabh.repository;

import com.saurabh.models.Chat;
import com.saurabh.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findByUsersId(Integer userId);

    @Query("Select c from Chat c Where :firstUser Member of c.users And :secondUser Member of c.users")
    Chat findChatByUserId(User firstUser, User secondUser );



}
