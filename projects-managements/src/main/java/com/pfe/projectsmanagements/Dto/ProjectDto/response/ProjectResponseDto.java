package com.pfe.projectsmanagements.Dto.ProjectDto.response;

import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistInfo;
import com.pfe.projectsmanagements.Dto.Team.response.TeamResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {
    private Long id ;
    private String name ;
    private String resume ;
    private Date creationDate ;
    private TeamResponseDto team ;
    private JournalistInfo projectChef ;
    private Set<TaskResp> tasks  = new HashSet<>();
    private String projectStatus;
    private String categoryName ;
    private ClientResp client ;
}
