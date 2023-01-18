package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Tach;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TachRepository extends MongoRepository<Tach,String> {

    public Tach findByName(String name );
}
