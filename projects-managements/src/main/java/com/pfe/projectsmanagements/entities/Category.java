package com.pfe.projectsmanagements.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("categories")
public class Category {
    @Transient
    public static final String SEQUENCE_NAME = "category_sequence";
    @Id
    private Long categoryId ;
    private String name ;
}
