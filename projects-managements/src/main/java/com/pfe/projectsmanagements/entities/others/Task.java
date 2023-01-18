package com.pfe.projectsmanagements.entities.others;

import com.pfe.projectsmanagements.Enums.TachStatus;
import com.pfe.projectsmanagements.entities.Tach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Task {

    @DBRef(lazy = true)
    private Tach tach ;
    private Long journalistId ;
    private TachStatus tachStatus;
    private String fileName ;
}
