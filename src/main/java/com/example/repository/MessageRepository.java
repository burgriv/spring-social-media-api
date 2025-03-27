package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Modifying
    @Query("delete from message m where m.messageId = :message_id")
    int customDelete(@Param("message_id") int message_id);

    @Modifying
    @Query("update message m set m.messageText = :message_text where m.messageId = :message_id")
    int customUpdate(@Param("message_text") String message_text, @Param("message_id") int message_id);

    List<Message> findAllByPostedBy(int posted_by);
}
