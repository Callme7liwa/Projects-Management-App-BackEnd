package com.pfe.projectsmanagements.services.Project;

import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistTachRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.AddMemberToProjectRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.ProjectRequestDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.ProjectResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.TaskResp;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Tach;
import com.pfe.projectsmanagements.entities.others.Task;

import java.util.List;
import java.util.Set;

public interface ProjectService {

    public ProjectResponseDto saveProject(ProjectRequestDto request);

    public ProjectResponseDto getProject(Long id);

    public Boolean addTachesToProjects(Long projectId , String taskName);

    public List<TaskResp> addListeTachesToProjects (Long projectId , List<String> taches);

    public Project getFullProject(Long id );

    public List<ProjectResponseDto> getProjects();

    public List<Project> getFullProjects();

    public Project updateProject(Long id , ProjectRequestDto projectRequestDto);

    public boolean deleteProject(Long id);

    public List<Project> getProjectFiniOfUser(Long journalistId);

    public boolean AffecterTacheToJournalist(Long projectId , JournalistTachRequest request) ;

    public ProjectResponseDto  changeStatusOfTach(Long projectId , String tachName , String status  );

    public List<Journalist> getTask(Long id );


    boolean addNewMemberToProject(Long projectId, Long journalistId);

    Boolean addProjectChef(Long projectId, Long chefId);
}

