package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client, Long> {

    Optional<Client> findByName(String name);
}
