package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    //custom query to find all messages based on posted_by value
    @Query("FROM Message WHERE posted_by = :posted_by")
    List<Message> findByPostedBy(Integer posted_by);

}
