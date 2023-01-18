package com.pfe.projectsmanagements.exceptions.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectUpdatingException extends  RuntimeException{
    private static final Long serialVersion  = 3L ;

    private String nameException ;
}
