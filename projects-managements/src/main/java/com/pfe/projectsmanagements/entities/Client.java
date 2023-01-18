package com.pfe.projectsmanagements.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("clients")
public class Client {

    @Transient
    public static final String SEQUENCE_NAME = "client_sequence";

    @Id
    private Long id ;
    private String name ;
    private String photo ;
    @DBRef
    private Set<Project> projects = new HashSet<>();
}
