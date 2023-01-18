package com.pfe.projectsmanagements.controllers.Project;

import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistTachRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.AddListTachesToProjectRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.AddMemberToProjectRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.AddTachToProjectRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.request.ProjectRequestDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.ProjectResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.TaskResp;
import com.pfe.projectsmanagements.Dto.ResponseFromApi;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.others.Task;
import com.pfe.projectsmanagements.services.Project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService ;

    public ProjectController(ProjectService projectService)
    {
        this.projectService = projectService ;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ProjectResponseDto> saveProject(@RequestBody ProjectRequestDto projectRequestDto)
    {
        System.out.println(projectRequestDto);
        ProjectResponseDto projectResponseDto = projectService.saveProject(projectRequestDto);
            if(Objects.nonNull(projectResponseDto))
                return new ResponseEntity<>(projectResponseDto , HttpStatus.ACCEPTED);
            throw new RuntimeException("Error while creating a project !");
    }

    @RequestMapping(value="/{id}"  , method = RequestMethod.GET, produces = "application/json"  , consumes = "application/json")
    public ResponseEntity<?> getFullProject(@PathVariable("id") Long id )
    {
        ProjectResponseDto project =  projectService.getProject(id);
        if(Objects.nonNull(project)){
            return new  ResponseEntity<>(project,HttpStatus.OK);
        }
        return new ResponseEntity<>("Project Not Found " , HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="" , method = RequestMethod.GET, produces = "application/json"  /*consumes = "application/json"*/)
    public ResponseEntity<List<ProjectResponseDto>> getProjects()
    {
        List<ProjectResponseDto> projects = projectService.getProjects();
        if(projects.size()>0) {
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT) ;
    }

    @RequestMapping(value="/fullProjects" , method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getFullProjects()
    {
        List<Project> projects = projectService.getFullProjects();
        if(projects.size()>0)
            return new ResponseEntity<>(projects , HttpStatus.OK);
        return new ResponseEntity<>("Data base doeas not contains any project already" , HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.PUT , produces = "application/json")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable("id") Long id , @RequestBody ProjectRequestDto projectRequestDto)
    {
        return null ;
    }

    @RequestMapping(value = "/addTacheToProject/{id}" , method =  RequestMethod.PUT, consumes = "application/json" , produces = "application/json")
    public ResponseEntity<Boolean> addTacheToProject(@PathVariable("id") Long id , @RequestBody AddTachToProjectRequest tachesName)
    {
        Boolean result =  projectService.addTachesToProjects(id,tachesName.getTachesName()) ;
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/addTachesToProject/{id}" , method =  RequestMethod.POST, consumes = "application/json" )
    public ResponseEntity<List<TaskResp>> addTachesToProject(@PathVariable("id") Long id , @RequestBody AddListTachesToProjectRequest tachesName)
    {
        System.out.println("list " + tachesName);
        List<TaskResp> tasks =  projectService.addListeTachesToProjects(id,tachesName.getTaches()) ;
        return  new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value="/affecterTache/{id}" , method = RequestMethod.POST , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> affecterTacheToJournaliste(@PathVariable("id") Long projectId , @RequestBody JournalistTachRequest journalistTachRequest)
    {
         boolean booleanResponse = projectService.AffecterTacheToJournalist(projectId,journalistTachRequest);
         if(booleanResponse)
         {
             return ResponseEntity.ok().body("Tach affected Succesfuly !!");
         }
            return ResponseEntity.badRequest().body(" Error while affecting the tach to project !");
    }

    @RequestMapping(value="/addNewMemberToProject/{id}" , method=RequestMethod.POST)
    public ResponseEntity<Boolean> addNewMemberToProject(@PathVariable("id") Long projectId , @RequestParam("employe") Long journalistId)
    {
        boolean booleanResponse = projectService.addNewMemberToProject(projectId , journalistId);
        return ResponseEntity.badRequest().body(true);
    }

    @RequestMapping(value="/{id}" , method = RequestMethod.DELETE , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id)
    {
        boolean booleanResult = projectService.deleteProject(id);
        if(booleanResult)
            return new ResponseEntity<>("Project is deleted", HttpStatus.OK);
        return new ResponseEntity<>("Error while deleting  the project  , " , HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/changeStatusOfTask/{id}")
    public ResponseEntity<ProjectResponseDto> changeStatusTask(@PathVariable("id") Long projectId , @RequestParam("tachName") String tachName , @RequestParam("status") String status)
    {
        ProjectResponseDto projectResponseDto = projectService.changeStatusOfTach(projectId , tachName , status);
        return new ResponseEntity<>(projectResponseDto , HttpStatus.OK);
    }

    @PostMapping(value="/addChefProject/{id}")
    public ResponseEntity<Boolean> addChefProject(@PathVariable("id") Long projectId , @RequestParam("chef") Long chefId)
    {
        Boolean booleanResponse = projectService.addProjectChef(projectId , chefId) ;
        return new ResponseEntity<>(booleanResponse , HttpStatus.OK);
    }



}
