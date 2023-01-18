package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken , Long> {
    Optional<RefreshToken> findByToken(String token);

    int deleteByJournalist(Journalist journalist);
}
