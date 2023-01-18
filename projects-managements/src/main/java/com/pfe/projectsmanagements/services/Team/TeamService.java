package com.pfe.projectsmanagements.services.Team;

import com.pfe.projectsmanagements.Dto.Team.request.TeamRequestDto;
import com.pfe.projectsmanagements.Dto.Team.response.TeamResponseDto;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Team;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeamService {

    public TeamResponseDto saveTeam(TeamRequestDto teamRequestDto);

    public TeamResponseDto getTeam(Long id);

    public Team getFullTeam(Long id );

    public List<Team> getFullTeams();

    public List<TeamResponseDto> getTeams();

    public Boolean affecterProject(Long teamId, Project project);

    public Team addProjectToTeam(Team team , Project project);

    public boolean addProjectToTeamFromController(Long teamId , Long projectId);

    public Boolean removeProject(Long teamId , Project project);

    public Boolean addJournalistToTeam(Long teamId , Long journalistId);


    boolean deleteJournalistFromTheTeam(Long teamService, Long journalistId);

    public boolean deleteProjectFromTeam (Long teamId , Project project);

    boolean uploadPicture(MultipartFile file, Long id);

    public Team addNewEmployeToTeam(Long teamId , Long journalistId);


}
