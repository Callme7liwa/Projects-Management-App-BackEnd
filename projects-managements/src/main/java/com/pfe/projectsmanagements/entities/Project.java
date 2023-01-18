package com.pfe.projectsmanagements.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfe.projectsmanagements.Enums.ProjectStatus;
import com.pfe.projectsmanagements.entities.others.Task;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document("projects")
public class Project {
    @Transient
    public static final String SEQUENCE_NAME = "project_sequence";

    @Id
    private Long id ;
    private String name ;
    private String resume ;
    private ProjectStatus status ;
    private Date creationDate ;
    @DBRef(lazy = true)
    @JsonIgnore
    private Team team ;
    @DBRef(lazy = true)
    private Journalist projectChef;
    @DBRef(lazy = true)
    private Category category ;
    @DBRef(lazy=true)
    private Client client ;
    private Set<Task> taches = new HashSet<>() ;
    private ProjectStatus projectStatus ;

}
