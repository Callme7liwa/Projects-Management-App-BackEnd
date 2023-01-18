package com.pfe.projectsmanagements.exceptions.Clients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientsExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> exception(ClientExistAlreadyException clientExistAlreadyException)
    {
        return new ResponseEntity<>("client existe already with this id" , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception(ClientUnFoundException clientUnFoundException)
    {
        return new ResponseEntity<>("cannot found client with this name or id " , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception(ClientContainProjectException clientContainProjectException)
    {
        return new ResponseEntity<>("This client already contain this project" , HttpStatus.BAD_REQUEST);
    }
}
