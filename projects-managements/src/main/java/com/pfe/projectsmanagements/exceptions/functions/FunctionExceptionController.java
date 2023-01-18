package com.pfe.projectsmanagements.exceptions.functions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FunctionExceptionController {
    @ExceptionHandler
    private ResponseEntity<String> exception(FunctionAlreadyExistException functionAlreadyExisteFunction)
    {
        return  new ResponseEntity<>("This function Already existe !" , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<String> exception(UnFoundFunctionException unFoundFunctionException)
    {
        return  new ResponseEntity<>("Cannot find function with this name  !" , HttpStatus.BAD_REQUEST);
    }
}
