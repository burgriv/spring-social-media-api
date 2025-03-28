package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

/**
 * MessageRepository manages the Message table in the database.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    List<Message> findAllByPostedBy(int posted_by);
}
