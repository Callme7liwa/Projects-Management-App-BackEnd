package com.pfe.projectsmanagements.entities;

import com.pfe.projectsmanagements.Enums.TachStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@Document("taches")
public class Tach {

    @Transient
    public static final String SEQUENCE_NAME = "tasks_sequence";
    @Id
    private Long id ;
    private String name ;
}
