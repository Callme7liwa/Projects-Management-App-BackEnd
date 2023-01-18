package com.pfe.projectsmanagements.mappers;

import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistInfo;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.ProjectRequestDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.ProjectResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.TaskResp;
import com.pfe.projectsmanagements.Dto.Team.response.TeamResponseDto;
import com.pfe.projectsmanagements.entities.Project;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class ProjectMapper extends SupperMapper<ProjectRequestDto, ProjectResponseDto, Project> {

    public static ProjectMapper projectMapper =null ;

    private TeamMapper teamMapper = null ;

    private JournalistMapper journalistMapper = JournalistMapper.getInstance() ;

    public static ProjectMapper getInstance()
    {
        if(projectMapper==null)
                  projectMapper = new ProjectMapper();
        return projectMapper ;
    }

    @Override
    public Project DtoToEntity(ProjectRequestDto projectRequestDto) {
        Project project = new Project();
        BeanUtils.copyProperties(projectRequestDto , project);
        return project ;
    }

    @Override
    public ProjectResponseDto EntityToDto(Project project) {
        System.out.println("cc");

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
         teamMapper = TeamMapper.getInstance();
         TeamResponseDto team = teamMapper.EntityToDto(project.getTeam());
         System.out.println("avant");
         BeanUtils.copyProperties(project,projectResponseDto);
        System.out.println("apres");

        project
            .getTaches()
                .stream()
                    .forEach(task -> {
                       TaskResp taskResp = new TaskResp();
                       taskResp.setTachStatus(task.getTachStatus().toString());
                       taskResp.setName(task.getTach().getName());
                       taskResp.setJournalistId(task.getJournalistId());
                       taskResp.setFileName(task.getFileName());
                       projectResponseDto.getTasks().add(taskResp);
                    });
         if(project.getProjectChef() !=null)
         {
             JournalistInfo projectChefInfo = journalistMapper.EntityToSpecialDto(project.getProjectChef());
             projectResponseDto.setProjectChef(projectChefInfo);
         }
         else
             projectResponseDto.setProjectChef(null);
        if(project.getClient()!=null)
            BeanUtils.copyProperties(projectResponseDto.getClient(),project.getClient());
        if(Objects.isNull(project.getProjectStatus()))
            projectResponseDto.setProjectStatus("PENDING");
        else
            projectResponseDto.setProjectStatus(project.getProjectStatus().toString());
        if(Objects.nonNull(project.getCategory()))
            projectResponseDto.setCategoryName(project.getCategory().getName());
        else
            projectResponseDto.setCategoryName("marketing");
        projectResponseDto.setTeam(team);
        return projectResponseDto;
    }
}
