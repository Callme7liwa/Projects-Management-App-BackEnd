package com.pfe.projectsmanagements.entities;

import com.pfe.projectsmanagements.Enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("roles")

public class JournalistRole {

    @Transient
    public static final String SEQUENCE_NAME = "role_sequence";

    private Long id ;
    private ERole role ;
}
