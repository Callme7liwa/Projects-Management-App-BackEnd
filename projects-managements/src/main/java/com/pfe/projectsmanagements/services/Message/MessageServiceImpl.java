package com.pfe.projectsmanagements.services.Message;

import com.pfe.projectsmanagements.Dto.Conversation.request.ConversationRequest;
import com.pfe.projectsmanagements.Dto.Conversation.response.ConversationResponse;
import com.pfe.projectsmanagements.Dto.Message.request.AddMessageToConversationRequest;
import com.pfe.projectsmanagements.Dto.Message.request.MessageRequest;
import com.pfe.projectsmanagements.Dto.Message.response.MessageResponse;
import com.pfe.projectsmanagements.Enums.MessageStatus;
import com.pfe.projectsmanagements.dao.ConversationRepository;
import com.pfe.projectsmanagements.entities.Conversation;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Tach;
import com.pfe.projectsmanagements.entities.others.MessagePayload;
import com.pfe.projectsmanagements.exceptions.Conversation.ConversationAlreadyExist;
import com.pfe.projectsmanagements.exceptions.Conversation.ConversationNotFoundException;
import com.pfe.projectsmanagements.exceptions.Conversation.MessageCannotBeSentException;
import com.pfe.projectsmanagements.mappers.ConversationMapper;
import com.pfe.projectsmanagements.services.Journalist.JournalistService;
import com.pfe.projectsmanagements.services.Project.ProjectService;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import com.pfe.projectsmanagements.services.Tache.TachService;
import com.pfe.projectsmanagements.services.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements  MessageService {

    @Autowired
    private JournalistService journalistService ;

    @Autowired
    private ProjectService projectService ;

    @Autowired
    private TachService tachService  ;

    @Autowired
    private ConversationRepository conversationRepository ;

    @Autowired
    private ImageService storageService;

    @Autowired
    private SequenceGeneratorService service;

    private ConversationMapper conversationMapper;

    public MessageServiceImpl(ConversationMapper conversationMapper) {
        this.conversationMapper = conversationMapper;
    }

    @Override
    public ConversationResponse newConversation(ConversationRequest request) {

        Journalist user1 = journalistService.getFullJournalist(request.getUser1Id());
        Journalist user2 = journalistService.getFullJournalist(request.getUser2Id());
        Tach tach = tachService.getTach(request.getTachName());
        Optional<Conversation> conversationOptional = conversationRepository.findByUser1(user1);
        Project project = projectService.getFullProject(request.getProjectId());

        if(conversationOptional.isPresent())
        {
            if(!conversationOptional.get().getProject().getId().equals(project.getId()) )
            {
                return saveConversation(project,user1,user2,tach);
            }
            else
            {
                if(! conversationOptional.get().getTach().getId().equals(request.getTachName()))
                    return saveConversation(project,user1,user2,tach);
                throw new ConversationAlreadyExist();
            }
        }
        return saveConversation(project,user1,user2,tach);
    }

    private ConversationResponse saveConversation ( Project project , Journalist user1 , Journalist user2 , Tach tach  )
    {
            Conversation newConversation = Conversation
                    .builder()
                    .id(service.getSequenceNumber(Conversation.SEQUENCE_NAME))
                    .user1(user1)
                    .user2(user2)
                    .project(project)
                    .tach(tach)
                    .lastMessageDate(new Date())
                    .build();
            Conversation response = conversationRepository.save(newConversation);
            return  conversationMapper.EntityToDto(response);
    }

    @Override
    public MessagePayload addMessageToConversation(Long conversationId, MultipartFile file , String body, Long senderId , Long receiverId) {

        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if(senderId  == receiverId)
             throw new MessageCannotBeSentException();
        if(conversationOptional.isPresent())
        {
            Conversation conversation = conversationOptional.get();
            /* */
            if((senderId != conversation.getUser1().getId() && senderId != conversation.getUser2().getId() )
                    ||( receiverId != conversation.getUser1().getId() && receiverId != conversation.getUser2().getId()))
                throw new MessageCannotBeSentException();
            /* */
            String fileName = "" ;
            if(!file.getOriginalFilename().equals("") ){
                storageService.save(file);
                fileName=file.getOriginalFilename();
            }
            MessagePayload messagePayload = MessagePayload
                    .builder()
                    .senderId(senderId)
                    .receiverId(receiverId)
                    .body(body)
                    .fileName(fileName)
                    .sendingDate( new Date())
                    .messageStatus(MessageStatus.NOT_VISITED)
                    .build();
            conversation.getMessages().add(messagePayload);
            conversation.setLastMessageDate(new Date());
            conversationRepository.save(conversation);
            return  messagePayload ;
        }
        throw new ConversationNotFoundException();
    }

    @Override
    public MessagePayload addMessageToConversationWithoutFile(Long conversationId, String body, Long senderId, Long receiverId) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if(senderId  == receiverId)
            throw new MessageCannotBeSentException();
        if(conversationOptional.isPresent())
        {
            Conversation conversation = conversationOptional.get();
            /* */
            if((senderId != conversation.getUser1().getId() && senderId != conversation.getUser2().getId() )
                    ||( receiverId != conversation.getUser1().getId() && receiverId != conversation.getUser2().getId()))
                throw new MessageCannotBeSentException();
            /* */

            MessagePayload messagePayload = MessagePayload
                    .builder()
                    .senderId(senderId)
                    .receiverId(receiverId)
                    .body(body)
                    .fileName(null)
                    .sendingDate( new Date())
                    .messageStatus(MessageStatus.NOT_VISITED)
                    .build();
            conversation.getMessages().add(messagePayload);
            conversation.setLastMessageDate(new Date());
            conversationRepository.save(conversation);
            return  messagePayload ;
        }
        throw new ConversationNotFoundException();
    }

    @Override
    public List<ConversationResponse> getConversations() {
        Comparator<Conversation> comparator = (c1,c2) -> {
            return c2.getLastMessageDate().compareTo(c1.getLastMessageDate());
        }   ;

        List<Conversation> conversations = conversationRepository.findAll();
        conversations.sort(comparator);

        System.out.println(" the data after " +conversations);
        List<ConversationResponse> conversationResponseList =
                    conversations
                            .stream()
                            .map(conversation -> conversationMapper.EntityToDto(conversation))
                            .collect(Collectors.toList());
        return conversationResponseList ;
    }

    @Override
    public List<ConversationResponse> getMessageOfUser(Long userId) {
        Comparator<Conversation> comparator = (c1,c2) -> {
            return c2.getLastMessageDate().compareTo(c1.getLastMessageDate());
        }   ;

        List<Conversation> conversations = conversationRepository.findAll() ;
        List<Conversation> conversationFiltred = conversations
                .stream()
                .filter(conversation -> conversation.getUser1().getId().equals(userId) || conversation.getUser2().getId().equals(userId))
                .collect(Collectors.toList());


        conversationFiltred.sort(comparator);

        List<ConversationResponse> conversationResponses =  conversationFiltred
                .stream()
                .map(conversation -> conversationMapper.EntityToSpecialDto(conversation))
                .collect(Collectors.toList());
        return conversationResponses;
    }


    @Override
    public boolean changeStatusOfMessage(Long conversationId , Long userAuthenticatedId) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if(conversationOptional.isPresent())
        {
            Conversation conversation = conversationOptional.get();
            int lastIndex = conversation.getMessages().size()-1 ;
            MessagePayload messagePayload = conversation.getMessages().get(lastIndex);
            if(messagePayload.getSenderId() == userAuthenticatedId )
                return false ;
            conversation.getMessages().get(lastIndex).setMessageStatus(MessageStatus.VISITED);
            conversationRepository.save(conversation);
            return true ;
        }
        throw new ConversationNotFoundException();
    }

}
