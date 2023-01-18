package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Conversation;
import com.pfe.projectsmanagements.entities.Journalist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, Long> {

    Optional<Conversation> findByUser1(Journalist user1);
}
