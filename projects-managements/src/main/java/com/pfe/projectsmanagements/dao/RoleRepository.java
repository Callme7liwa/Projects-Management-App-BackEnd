package com.pfe.projectsmanagements.dao;

import com.pfe.projectsmanagements.entities.JournalistRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<JournalistRole, Long> {

    Optional<JournalistRole> findByRole(String roleName);
}
