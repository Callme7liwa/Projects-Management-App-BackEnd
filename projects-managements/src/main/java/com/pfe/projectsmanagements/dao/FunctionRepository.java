package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Function;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunctionRepository extends MongoRepository<Function , Long> {

    Optional<Function> findByName(String name);
}
