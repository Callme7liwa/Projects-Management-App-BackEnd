package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Journalist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JournalistRepository extends MongoRepository<Journalist,Long> {

    Optional<Journalist> findByUserName(String username);

    Optional<Journalist> findByEmail(String email);
}
