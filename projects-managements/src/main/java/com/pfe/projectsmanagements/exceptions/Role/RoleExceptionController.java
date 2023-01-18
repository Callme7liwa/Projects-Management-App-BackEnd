package com.pfe.projectsmanagements.exceptions.Role;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoleExceptionController {

    @ExceptionHandler
    public ResponseEntity<?> exception(RoleNotFoundException roleNotFoundException)
    {
        return new ResponseEntity<>("Unfound Role  !! ", HttpStatus.BAD_REQUEST);
    }
}
