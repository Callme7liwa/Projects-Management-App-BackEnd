package com.pfe.projectsmanagements.entities.others;

import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Tach;
import com.pfe.projectsmanagements.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Affectation {

    @DBRef
    private Project project ;
    @DBRef
    private Team team ;
    @DBRef
    private List<Tach> taches = new ArrayList<Tach>();
}
