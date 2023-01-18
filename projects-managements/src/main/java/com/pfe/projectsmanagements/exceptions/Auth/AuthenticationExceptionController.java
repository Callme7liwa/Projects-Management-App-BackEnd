package com.pfe.projectsmanagements.exceptions.Auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> exception(AuthenticationUnsuccesfulException authenticationUnsuccesfulException)
    {
        return new  ResponseEntity<>("Bad Credentials ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception(PasswordDoesNotEqual passwordDoesNotEqual)
    {
        return new ResponseEntity<>("Password does not equals !" , HttpStatus.BAD_REQUEST);
    }
}
