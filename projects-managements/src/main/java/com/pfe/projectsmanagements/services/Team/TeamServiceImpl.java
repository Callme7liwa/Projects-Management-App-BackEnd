package com.pfe.projectsmanagements.services.Team;

import com.pfe.projectsmanagements.Dto.Team.request.TeamRequestDto;
import com.pfe.projectsmanagements.Dto.Team.response.TeamResponseDto;
import com.pfe.projectsmanagements.dao.TeamRepository;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Team;
import com.pfe.projectsmanagements.exceptions.Journalist.JournalistNotFoundException;
import com.pfe.projectsmanagements.exceptions.Project.ProjectAlreadyToTheTeamException;
import com.pfe.projectsmanagements.exceptions.Project.ProjectUnfoundException;
import com.pfe.projectsmanagements.exceptions.Team.AddJournalistToTeamException;
import com.pfe.projectsmanagements.exceptions.Team.TeamAlreadyExisteException;
import com.pfe.projectsmanagements.exceptions.Team.TeamNotFoundException;
import com.pfe.projectsmanagements.mappers.TeamMapper;
import com.pfe.projectsmanagements.services.Journalist.JournalistService;
import com.pfe.projectsmanagements.services.Project.ProjectService;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import com.pfe.projectsmanagements.services.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private TeamMapper teamMapper ;

    private TeamRepository teamRepository;

    @Autowired
    private ProjectService projectService ;

    @Autowired
    private JournalistService journalistService  ;

    @Autowired
    private SequenceGeneratorService service;

    @Autowired
    private ImageService storageService ;

    private  Random random ;

    public TeamServiceImpl(Random random, TeamRepository teamRepository ) {
        this.teamMapper = TeamMapper.getInstance();
        this.teamRepository = teamRepository;
        this.random = new Random();
    }


    @Override
    // Get the team -> if not exist -> add journalist to team -> save the team || save the team on the journalist Document
    public TeamResponseDto saveTeam(TeamRequestDto teamRequestDto) {
        Optional<Team> teamSearch = teamRepository.findByName(teamRequestDto.getName());
        System.out.println("team  : {}"+teamSearch.toString());
        if(teamSearch.isEmpty()) {

            Team team = teamMapper.DtoToEntity(teamRequestDto);
            Long teamId = service.getSequenceNumber(Team.SEQUENCE_NAME);
            team.setId(teamId);
            team.setName("team "+teamId);
            team.setPhoto(teamId+".jpg");
            team.setCreationDate(new Date());
            team.setNumberEtoiles(0);

            if (teamRequestDto.getJournalistsId().size() > 0) {
                Set<Journalist> journalists = teamRequestDto
                        .getJournalistsId()
                        .stream()
                        .map(id -> {
                            Journalist journalist = journalistService.getFullJournalist(id);
                            return journalist;
                        }).collect(Collectors.toSet());
                team.setJournalists(journalists);
            }
            team = teamRepository.save(team);

            if (Objects.nonNull(team))
                return teamMapper.EntityToDto(team);
            return null;
        }

        throw new TeamAlreadyExisteException();
    }

    @Override
    public TeamResponseDto getTeam(Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if(teamOptional.isPresent())
            return teamMapper.EntityToDto(teamOptional.get());
        throw new TeamNotFoundException();
    }

    @Override
    public Team getFullTeam(Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if(teamOptional.isPresent())
            return teamOptional.get();
        throw  new TeamNotFoundException();
    }

    @Override
    public List<Team> getFullTeams() {
        Optional<List<Team>> teams = Optional.of(teamRepository.findAll());
        if(teams.isPresent())
            return teams.get();
        throw  null ;
    }

    @Override
    public List<TeamResponseDto> getTeams() {
        Optional<List<Team>> teams = Optional.of(teamRepository.findAll());
        if(teams.isPresent())
            return
                teams
                        .get()
                        .stream()
                        .map(team ->  teamMapper.EntityToDto(team))
                        .collect(Collectors.toList());
        return null ;
    }

    @Override
    public Boolean affecterProject(Long teamId, Project project) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if(teamOptional.isPresent()) {
            Team team = teamOptional.get();
            team.getProjects().add(project);
            teamRepository.save(team);
            return Boolean.TRUE;
        }
        throw new TeamNotFoundException();
    }

    @Override
    public Boolean removeProject(Long teamId, Project project) {
        return null;
    }

    // Get journalist & team -> Search The journalist if he's exist in team already  .
    @Override
    public Boolean addJournalistToTeam(Long teamId, Long journalistId) {
        Journalist journalist = journalistService.getFullJournalist(journalistId);
        Optional<Team> teamOptional = teamRepository.findById(teamId) ;
        if(teamOptional.isPresent())
        {
            Team team  =  teamOptional.get();
            Optional<Journalist>  journalistExist = team
                            .getJournalists()
                            .stream()
                            .filter(journalist1 -> journalist1.getId().equals(journalist.getId()))
                            .findFirst();

            if(journalistExist.isPresent()) {
                throw new AddJournalistToTeamException();
            }

            team.getJournalists().add(journalist);
            //
            //journalistService.addTeamToJournalist(journalist , team );
            //
            team = teamRepository.save(team);
            //
            return true ;
        }
        throw new TeamNotFoundException();
    }

    // called form an externel service
    @Override
    public Team addProjectToTeam(Team team, Project project) {
        if(Objects.nonNull(team) && Objects.nonNull(project)) {
            team.getProjects().add(project);
            teamRepository.save(team);
            return team ;
        }
        else {
            if (Objects.isNull(team))
                throw new TeamNotFoundException();
            if (Objects.isNull(project))
                throw new ProjectUnfoundException();
            return null ;
        }
    }

    @Override
    public boolean addProjectToTeamFromController(Long teamId, Long projectId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if(teamOptional.isPresent())
        {
            Team team  = teamOptional.get();
            Optional<Long> teamIdSearch = team
                                        .getProjects()
                                        .stream()
                                        .filter(project -> project.getId().equals(projectId))
                                        .map(project -> project.getId())
                                        .findFirst();
            if(teamIdSearch.isEmpty())
            {
                Project project = projectService.getFullProject(projectId);
                team.getProjects().add(project);
                teamRepository.save(team);
                return true ;
            }
            throw new ProjectAlreadyToTheTeamException();
        }
        throw new ProjectUnfoundException();
    }

    @Override
    public boolean deleteJournalistFromTheTeam(Long teamService, Long journalistId) {
      /*  Optional<Team> teamOptional = teamRepository.findById(teamService) ;
        if(teamOptional.isPresent())
        {
            Journalist journalist = journalistService.getFullJournalist(journalistId);
            Team team = teamOptional.get();
            team.getJournalists().remove(journalist);

        }*/
        return true ;
    }

    @Override
    public boolean deleteProjectFromTeam(Long teamId, Project project) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if(teamOptional.isPresent())
        {
            Team team = teamOptional.get();
            Optional<Project> projectOptional =
                        team
                                .getProjects()
                                .stream()
                                .filter(project1 -> project.getId().equals(project1.getId()))
                                .findFirst() ;
            if(projectOptional.isPresent())
            {
                team.getProjects().remove(project);
                teamRepository.save(team);
                return true ;
            }
            throw new ProjectUnfoundException() ;
        }
        throw new TeamNotFoundException();
    }

    @Override
    @Transactional
    public boolean uploadPicture(MultipartFile file, Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if(teamOptional.isPresent())
        {
            Team team  = teamOptional.get();

            String extension = ".jpg";
            if(file.getContentType().toString() == "image/jpeg")
                extension = ".jpg";
            else
            if(file.getContentType().toString() == "image/png")
                extension=".png";

            String fileName = UUID.randomUUID().toString()+extension ;
            storageService.fileUploadToServer("teams",fileName , file);
            team.setPhoto(fileName);
            teamRepository.save(team);
            return true ;
        }
        throw new TeamNotFoundException();
    }

    @Override
    public Team addNewEmployeToTeam(Long teamId, Long journalistId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if(teamOptional.isPresent())
            return teamOptional.get() ;
        throw new TeamNotFoundException();
    }
}
