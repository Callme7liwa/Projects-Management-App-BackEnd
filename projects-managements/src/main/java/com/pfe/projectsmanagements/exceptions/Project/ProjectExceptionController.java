package com.pfe.projectsmanagements.exceptions.Project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class

ProjectExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> exception(ProjectUnfoundException projectUnfoundException) {
        return new ResponseEntity<>("Project Unfound ! " , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception(ProjectUpdatingException projectUpdatingException)
    {
        return  new ResponseEntity<>(projectUpdatingException.getNameException() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception (ProjectDoesNotContaineThisTask projectDoesNotContaineThisTask)
    {
        return new ResponseEntity<>(" Project does not contain this Exception " , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception(ProjectAlreadyToTheTeamException projectAlreadyToTheTeamException)
    {
        return new ResponseEntity<>("This project Already to this team !",HttpStatus.BAD_REQUEST);
    }
}
