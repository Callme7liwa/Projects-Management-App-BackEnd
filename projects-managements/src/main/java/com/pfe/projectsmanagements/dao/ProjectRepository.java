package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository  extends MongoRepository<Project , Long> {
}
