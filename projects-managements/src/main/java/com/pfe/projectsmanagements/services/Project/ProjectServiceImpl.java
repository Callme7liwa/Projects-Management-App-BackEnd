package com.pfe.projectsmanagements.services.Project;

import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistTachRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.ProjectRequestDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.ProjectResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.TaskResp;
import com.pfe.projectsmanagements.Enums.ProjectStatus;
import com.pfe.projectsmanagements.Enums.TachStatus;
import com.pfe.projectsmanagements.dao.ProjectRepository;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Tach;
import com.pfe.projectsmanagements.entities.Team;
import com.pfe.projectsmanagements.entities.others.Task;
import com.pfe.projectsmanagements.exceptions.Project.ProjectDoesNotContaineThisTask;
import com.pfe.projectsmanagements.exceptions.Project.ProjectUnfoundException;
import com.pfe.projectsmanagements.exceptions.Tach.TachAlreadyAffectedException;
import com.pfe.projectsmanagements.exceptions.Tach.TachUnfoundException;
import com.pfe.projectsmanagements.mappers.ProjectMapper;
import com.pfe.projectsmanagements.services.Journalist.JournalistService;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import com.pfe.projectsmanagements.services.Tache.TachService;
import com.pfe.projectsmanagements.services.Team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl  implements  ProjectService{

    private Random random ;

    private ProjectMapper projectMapper ;

    private ProjectRepository projectRepository;

    private TachService tachService ;

    private TeamService teamService ;

    private JournalistService journalistService ;

    @Autowired
    private SequenceGeneratorService service;



    public ProjectServiceImpl(ProjectRepository projectRepository, Random random, @Lazy TachService tachService,  @Lazy TeamService teamService,  @Lazy JournalistService journalistService){
        this.tachService = tachService;
        this.teamService = teamService;
        this.journalistService = journalistService;
        this.projectMapper = ProjectMapper.getInstance();
        this.projectRepository = projectRepository;
        this.random = random ;
    }


    @Override
    @Transactional
    // Map To Dto -> Search Tach by name -> get team -> save project  ||   add project to team  || add project and team to the journalist (affectation)
    public ProjectResponseDto saveProject(ProjectRequestDto request) {
        Project  project = projectMapper.DtoToEntity(request);
        if(request.getTachesNames().size()>0)
        {
            Set<Task> taches =
                    request.getTachesNames()
                            .stream()
                            .map(name->{
                                Tach tach =  tachService.getTach(name);
                                Task task = new Task();
                                task.setTach(tach);
                                task.setTachStatus(TachStatus.PENDING);
                                return  task ;
                            }).collect(Collectors.toSet());
            project.setTaches(taches);
        }
        Team team = teamService.getFullTeam(request.getTeamId());
        Journalist chefProject = journalistService.getFullJournalist(request.getChefId());
        project.setProjectChef(chefProject);
        project.setCreationDate(new Date());
        Long projectId = service.getSequenceNumber(Project.SEQUENCE_NAME);
        project.setId(projectId);
        project.setProjectStatus(ProjectStatus.PENDING);
        project.setTeam(team);
        Project projectFinal  = projectRepository.save(project);
        teamService.addProjectToTeam(team,projectFinal);
        team
            .getJournalists()
                .forEach(journalist -> {
                    journalistService.addProjectAndTeamToJournalist(journalist,projectFinal);
                });
        return projectMapper.EntityToDto(project);
    }

    @Override
    public ProjectResponseDto getProject(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if(projectOptional.isPresent())
            return projectMapper.EntityToDto(projectOptional.get());
        throw new ProjectUnfoundException();
    }

    // get Project -> get list of tache and add it to the project -> save
    @Override
    public Boolean addTachesToProjects(Long projectId ,String taskName) {
        Optional <Project> projectOptional = projectRepository.findById(projectId);
       /* if(projectOptional.isPresent())
        {
            Project project = projectOptional.get();
            tachesName
                    .stream()
                        .map(name->{
                            Tach tach = tachService.getTach(name);
                            return tach ;
                            })
                            .forEach(tach -> {
                                Task task  = new Task();
                                task.setTach(tach);
                                task.setTachStatus(TachStatus.PENDING);
                                project.getTaches().add(task);
                            });
            Project result = projectRepository.save(project);
            if(Objects.nonNull(result))
                return result ;
            throw new ProjectUpdatingException("Error while saving the project ! ");
        }
        throw new ProjectUnfoundException();*/
        if(projectOptional.isPresent())
        {
            Project project = projectOptional.get();
            Optional<Long> taskId = project
                    .getTaches()
                    .stream()
                    .filter(task -> task.getTach().getName().equals(taskName))
                    .map(task -> task.getTach().getId())
                    .findFirst();
            if(taskId.isEmpty())
            {
                Tach tach = tachService.getTach(taskName);
                Task task  = new Task();
                task.setTach(tach);
                task.setTachStatus(TachStatus.PENDING);
                Project result = projectRepository.save(project);
                if(Objects.nonNull(result))
                    return Boolean.TRUE  ;
                return Boolean.FALSE;
            }
            throw new  TachAlreadyAffectedException();

        }
        throw new ProjectUnfoundException();
    }

    @Override
    public List<TaskResp> addListeTachesToProjects(Long projectId, List<String> taches) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        System.out.println(projectOptional.get());
        System.out.println("etap 1 "  + taches);
        if(projectOptional.isPresent())
        {
            System.out.println("etap 2 ");

            Project project = projectOptional.get();
            System.out.println("etap 3");
            List<TaskResp> tasks = taches
                    .stream()
                    .map(name->{
                        Tach tach = tachService.getTach(name);

                        // Verifier si la tach existe deja dans le project  !

                       Optional<Task> taskOptionalSearch =  project
                                .getTaches()
                                .stream()
                                .filter(task -> task.getTach().getName().equals(name))
                                .findFirst();

                       if(taskOptionalSearch.isEmpty()) {
                           Task task = new Task();
                           task.setTach(tach);
                           task.setTachStatus(TachStatus.PENDING);
                           project.getTaches().add(task);
                           TaskResp taskResp = new TaskResp();
                           taskResp.setName(task.getTach().getName());
                           taskResp.setTachStatus(task.getTachStatus().toString());
                           taskResp.setJournalistId(taskResp.getJournalistId());
                           return taskResp;
                       }
                       throw new TachAlreadyAffectedException();
                    }).collect(Collectors.toList());
            projectRepository.save(project);
            return tasks ;
        }
        System.out.println("not found ");
        throw new ProjectUnfoundException();
    }

    @Override
    public Project getFullProject(Long id) {
        Optional <Project> projectOptional = projectRepository.findById(id);
        if(projectOptional.isPresent())
            return projectOptional.get();
        throw new ProjectUnfoundException() ;
    }

    @Override
    public List<ProjectResponseDto> getProjects() {
        return projectRepository
                .findAll()
                .stream()
                .map(project -> {
                    return projectMapper.EntityToDto(project);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> getFullProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project updateProject(Long id, ProjectRequestDto projectRequestDto) {
        return null;
    }

    @Override
    public boolean deleteProject(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if(projectOptional.isPresent())
        {
            System.out.println("okokooko");
            Project project = projectOptional.get();
            project
                    .getTeam()
                    .getJournalists()
                    .stream()
                    .map(
                            journalist -> journalistService.removeProjectFromJournalist(journalist.getId(),project)
                    );
            System.out.println("okokooko");
           teamService.deleteProjectFromTeam(project.getTeam().getId() , project);
            System.out.println("okokooko");
            projectRepository.delete(project);
           return true ;
        }
        throw new ProjectUnfoundException();
    }

    @Override
    public List<Project> getProjectFiniOfUser(Long journalistId) {
        return null ;
    }

    //Get Project By Id ->  Get Tach By NAme  -> ajouter la tach au journalist
    //Si Est Ok , on chercher la tach dans le project  , si il existe on vas aller
    @Override
    public boolean AffecterTacheToJournalist(Long projectId, JournalistTachRequest request) {
        System.out.println("project Id : " + projectId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(projectOptional.isPresent())
        {
            Project project = projectOptional.get();
            // On Cherche d'abord si la tach existe , sinon on fait rien .
            Optional<Tach> tachSearch = project
                    .getTaches()
                    .stream()
                    .filter(task -> task.getTach().getName().equals(request.getTachName()))
                    .map(task -> task.getTach())
                    .findFirst();
            if(tachSearch.isPresent()) {
                // On verifie si la tach si elle est deja affecter !
                Optional<Tach> tachSearchIfAffected = project
                        .getTaches()
                        .stream()
                        .filter(task -> task.getTach().getName().equals(request.getTachName()))
                        .filter(task -> task.getTachStatus().equals(TachStatus.PENDING))
                        .map(task -> task.getTach())
                        .findFirst();
                //Si elle est encore en liste d'attente on continue .
                if(tachSearchIfAffected.isPresent())
                {
                    Tach tach = tachService.getTach(request.getTachName());
                    // On Ajoute la tach pour l employe a lautre cote , et on obtien une repose .
                    boolean booleanResponse = journalistService
                            .addTachToJournalist(request.getJournalistId(), project.getId(), tach);
                    // si elle est vrai on continue .
                    if (booleanResponse) {
                        // Je recupere la task a fin de la modifier ( Ajouter l employe , changer le status ) //
                        Optional<Task> taskOptional = project
                                .getTaches()
                                .stream()
                                .filter(task -> task.getTach().getName().equals(tach.getName()))
                                .findFirst();
                        // Si elle exite on continue
                        if (taskOptional.isPresent()) {
                            Task task = taskOptional.get();
                            // On supprime l'ancienne
                            project.getTaches().remove(task);
                            Journalist journalist = journalistService.getFullJournalist(request.getJournalistId());
                            task.setJournalistId(journalist.getId());
                            // une fois la tach es affectée on change le status pour working
                            task.setTachStatus(TachStatus.WORKING);
                            project.getTaches().add(task);
                            project.setProjectStatus(ProjectStatus.WORKING);
                            projectRepository.save(project);
                            return true;
                        }
                        throw new TachUnfoundException();
                    }
                }
                throw new TachAlreadyAffectedException();
            }
            throw new ProjectDoesNotContaineThisTask();
        }
        throw new ProjectUnfoundException();
    }

    @Override
    public ProjectResponseDto changeStatusOfTach(Long projectId, String tachName , String status ) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(projectOptional.isPresent())
        {
            Project project  = projectOptional.get();
            Optional<Task> taskOptional = project
                    .getTaches()
                    .stream()
                    .filter(task -> task.getTach().getName().equals(tachName))
                    .findFirst();
            if(taskOptional.isPresent()) {
                Task task = taskOptional.get();
                project.getTaches().remove(task);
                switch (status) {
                    case "WORKING":
                        task.setTachStatus(TachStatus.WORKING);
                        break ;
                    case "DELIVERY":
                        task.setTachStatus(TachStatus.DELIVERY);
                        break ;
                    case "FINISHED":
                        task.setTachStatus(TachStatus.FINISHED);
                    case "REJECTED":
                        task.setTachStatus(TachStatus.REJECTED);
                        break ;
                    default:
                        task.setTachStatus(TachStatus.PENDING);
                }
                project.getTaches().add(task);
                Project projectResponse = projectRepository.save(project);
                return projectMapper.EntityToDto(project);
            }
            throw new TachUnfoundException();
        }
        throw new ProjectUnfoundException();

    }


    // Ajouter un nouveau membre au project
    //  modifier la team ( ajouter l employe a la team )
    // de meme a l'autre cote pour l'employe , on ajoute une nouvelle affectation (project , team )
    @Override
    public boolean addNewMemberToProject(Long projectId, Long  journalistId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(projectOptional.isPresent())
        {
            Project project = projectOptional.get();
            Team team = project.getTeam();
            Journalist journalist = journalistService.getFullJournalist(journalistId);
            System.out.println("okoko");
            teamService.addJournalistToTeam(team.getId() , journalist.getId());
            System.out.println("ssss");
            journalistService.addProjectAndTeamToJournalist(journalist , project);
            System.out.println("ssss");

            projectRepository.save(project);
            return true ;
        }
        throw new ProjectUnfoundException();
    }

    @Override
    public Boolean addProjectChef(Long projectId, Long chefId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(projectOptional.isPresent())
        {
            Project project = projectOptional.get();
            Journalist projectChef = journalistService.getFullJournalist(chefId);
            project.setProjectChef(projectChef);
            teamService.addJournalistToTeam(project.getTeam().getId() , projectChef.getId());
            journalistService.addProjectAndTeamToJournalist(projectChef , project);
            projectRepository.save(project);
            return true ;
        }
        throw new ProjectUnfoundException();
    }

    /*public boolean verifierAffectation ( Long journalistId , Long projectId , Long teamId)
    {
        journalistService.verifierAffectation(journalistId, projectId , teamId);
    }*/

    @Override
    public List<Journalist> getTask(Long id) {
        /*Project project = projectRepository.findById(id).get();
        if(project!=null) {
            List<Journalist> journalists =
                    project.getTaches().stream().map(task -> {
                        System.out.println(task.get());
                        return task.getJournalist();
                    }).collect(Collectors.toList());
            return  journalists ;
        }*/
        return null ;
    }

}
