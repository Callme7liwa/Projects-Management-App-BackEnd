package com.pfe.projectsmanagements.controllers.Message;

import com.pfe.projectsmanagements.Dto.Conversation.request.ConversationRequest;
import com.pfe.projectsmanagements.Dto.Conversation.response.ConversationResponse;
import com.pfe.projectsmanagements.Dto.Message.request.MessageRequest;
import com.pfe.projectsmanagements.Dto.Message.response.MessageResponse;
import com.pfe.projectsmanagements.entities.Conversation;
import com.pfe.projectsmanagements.entities.others.MessagePayload;
import com.pfe.projectsmanagements.services.Message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/conversations")
public class MessageController {

    @Autowired
    private MessageService messageService ;

    @PostMapping("")
    private ResponseEntity<ConversationResponse> saveConversation(@RequestBody ConversationRequest request)
    {
        System.out.println(request);
        ConversationResponse conversationResponse = messageService.newConversation(request);
        return new ResponseEntity<>(conversationResponse , HttpStatus.OK);
    }

    @PostMapping("/saveMessageWithFile/{id}")
    private ResponseEntity<MessagePayload> saveMessage(@PathVariable("id") Long conversationId , @RequestParam("sender") Long sender ,  @RequestParam("receiver") Long receiver ,  @RequestParam("body") String body , @RequestParam("file")MultipartFile file)
    {
       MessagePayload messagePayload =  messageService.addMessageToConversation(conversationId , file , body , sender , receiver);
       return new ResponseEntity<>(messagePayload , HttpStatus.OK);
    }

    @PostMapping("/saveMessageWithoutFile/{id}")
    private ResponseEntity<MessagePayload> saveMessage(@PathVariable("id") Long conversationId , @RequestParam("sender") Long sender ,  @RequestParam("receiver") Long receiver ,  @RequestParam("body") String body )
    {
        MessagePayload messagePayload =  messageService.addMessageToConversationWithoutFile(conversationId  , body , sender , receiver);
        return new ResponseEntity<>(messagePayload , HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    private ResponseEntity< List<ConversationResponse> > getMessagesOfUser(@PathVariable("id") Long userId)
    {
        System.out.println("okoeoekokoeko");
        List<ConversationResponse> conversationResponseList = messageService.getMessageOfUser(userId);
        return new ResponseEntity<>(conversationResponseList , HttpStatus.OK);

    }

    @GetMapping("")
    private ResponseEntity<List<ConversationResponse>> getConversations()
    {
        List<ConversationResponse> conversationResponseList = messageService.getConversations();
        return new ResponseEntity<>(conversationResponseList , HttpStatus.OK);
    }

    @PostMapping("/changeStatusMessage/{id}")
    private ResponseEntity<Boolean> changeStatusOfMessage(@PathVariable("id") Long conversationId , @RequestParam("user") Long userId )
    {
        boolean response = messageService.changeStatusOfMessage(conversationId,userId);
        if(response)
            return  new ResponseEntity<>(response , HttpStatus.OK);
        return new ResponseEntity<>(response , HttpStatus.EXPECTATION_FAILED);
    }



}
