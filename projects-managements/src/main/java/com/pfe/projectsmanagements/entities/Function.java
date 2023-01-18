package com.pfe.projectsmanagements.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.security.DenyAll;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document("functions")
public class Function {

    @Transient
    public static final String SEQUENCE_NAME = "function_sequence";

    @Id
    private Long id ;
    @NotNull(message = " The function could not be null !")
    private String name ;
}
