package com.pfe.projectsmanagements.Dto.Journalist.response;

import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalistAffectationResponse {
    private Team team ;
    private Project project ;
    private List<String> taches = new ArrayList<>();
}
