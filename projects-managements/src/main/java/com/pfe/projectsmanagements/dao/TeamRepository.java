package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends MongoRepository<Team, Long> {

    public Optional<Team> findByName(String name);
}
