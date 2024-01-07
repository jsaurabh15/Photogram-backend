package com.saurabh.repository;

import com.saurabh.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

    List<Story> findByUserId(Integer userId);
}

