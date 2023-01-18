package com.pfe.projectsmanagements.controllers.Team;

import com.pfe.projectsmanagements.Dto.Team.request.AddProjectToTeamRequest;
import com.pfe.projectsmanagements.Dto.Team.request.TeamAddJournalistRequest;
import com.pfe.projectsmanagements.Dto.Team.request.TeamDeleteJournalistRequest;
import com.pfe.projectsmanagements.Dto.Team.request.TeamRequestDto;
import com.pfe.projectsmanagements.Dto.Team.response.TeamResponseDto;
import com.pfe.projectsmanagements.entities.Team;
import com.pfe.projectsmanagements.services.Team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("")
    public ResponseEntity<?> saveTeam(@RequestBody TeamRequestDto teamRequestDto)
    {
        TeamResponseDto teamResponseDto = teamService.saveTeam(teamRequestDto);
        if(Objects.nonNull(teamResponseDto))
            return ResponseEntity.status(HttpStatus.OK).body(teamResponseDto);
        throw new RuntimeException("Error occure while creating a new team ! ");
    }

    @GetMapping("/{id}")
    public TeamResponseDto getTeam(@PathVariable("id") Long id)
    {
        return teamService.getTeam(id);
    }

    @GetMapping("/full/{id}")
    public Team getFullTeam(@PathVariable("id") Long id)
    {
        return teamService.getFullTeam(id);
    }

    @GetMapping("")
    public ResponseEntity<List<TeamResponseDto>> getAllTeams()
    {
        List<TeamResponseDto> response = teamService.getTeams();
        if(response.size() > 0)
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }



    @RequestMapping(value = "/addJournalistToTeam/{id}" , method = RequestMethod.POST   )
    public ResponseEntity<?> addJournalistToTeam(@PathVariable("id") Long id , @RequestParam("employe") Long journalistId)
    {
        System.out.println("im here ");
        Boolean booleanResponse= teamService.addJournalistToTeam(id,journalistId);
        /*if(booleanResponse)
            return new ResponseEntity<>(booleanResponse,HttpStatus.OK);*/
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value="/removeJournalistFromTeam/{id}" , method = RequestMethod.DELETE , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> deleteJournalistFromTeam(@PathVariable("id") Long id , @RequestBody TeamDeleteJournalistRequest request)
    {
        //boolean isDeletedFromTheTeam = teamService.deleteJournalistFromTheTeam(id , request.getJournalistId());
        return null ;
    }

    @RequestMapping(value="/addProjectToTeam/{id}"  , method = RequestMethod.PUT , produces = "application/json")
    public ResponseEntity<String> addProjectToTeam(@PathVariable("id") Long teamId , @RequestBody AddProjectToTeamRequest addProjectToTeamRequest)
    {
        boolean booleanResponse = teamService.addProjectToTeamFromController(teamId , addProjectToTeamRequest.getProjectId());
        if(booleanResponse)
            return new  ResponseEntity<>("Projects added succesfuly to  team !!",HttpStatus.OK);
        return new  ResponseEntity<>("Projects added succesfuly to  team !!",HttpStatus.OK);
    }

    @PostMapping("/addEmploye/{id}")
    public ResponseEntity<Team> addNewEmployeeToTeam(@PathVariable("id") Long teamId , @RequestParam("employee") Long journalistId)
    {
        Team teamResponse = teamService.addNewEmployeToTeam(teamId , journalistId);
        if(Objects.nonNull(teamResponse))
            return new ResponseEntity<>(teamResponse, HttpStatus.OK) ;
        return new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
    }


}
