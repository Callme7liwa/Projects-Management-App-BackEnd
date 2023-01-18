package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category , Long> {
}
