package com.pfe.projectsmanagements.Dto.Journalist.response;

import com.pfe.projectsmanagements.Dto.ProjectDto.response.ClientResp;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.TaskResp;
import com.pfe.projectsmanagements.Dto.Team.response.TeamResponseDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AffectationsResponse {
    private Long id ;
    private String name ;
    private String resume ;
    private Date creationDate ;
    private TeamResponseDto team ;
    private Set<TaskResp> tasks  = new HashSet<>();
    private String projectStatus;
    private String categoryName ;
    private ClientResp client ;
}
