package com.pfe.projectsmanagements.exceptions.Conversation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConversationExceptionController {
    @ExceptionHandler
    public ResponseEntity<String> exception (ConversationAlreadyExist conversationAlreadyExist)
    {
        return new ResponseEntity<>("Conversation already Existe" , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception (ConversationNotFoundException conversationNotFoundException)
    {
        return new ResponseEntity<>("Conversation Not Found  ! " , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exception (MessageCannotBeSentException messageCannotBeSentException)
    {
        return new ResponseEntity<>("Message Cannot be sent  ! " , HttpStatus.BAD_REQUEST);
    }


}
