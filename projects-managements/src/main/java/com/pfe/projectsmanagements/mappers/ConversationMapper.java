package com.pfe.projectsmanagements.mappers;

import com.pfe.projectsmanagements.Dto.Conversation.others.Project;
import com.pfe.projectsmanagements.Dto.Conversation.request.ConversationRequest;
import com.pfe.projectsmanagements.Dto.Conversation.response.ConversationResponse;
import com.pfe.projectsmanagements.Dto.Message.request.MessageRequest;
import com.pfe.projectsmanagements.Dto.Message.response.MessageResponse;
import com.pfe.projectsmanagements.Dto.Message.response.Others.ProjectMessage;
import com.pfe.projectsmanagements.entities.Conversation;

import java.util.ArrayList;

public class ConversationMapper extends   SupperMapper<ConversationRequest, ConversationResponse, Conversation>{

    private static ConversationRequest instance = null;

    private JournalistMapper journalistMapper = JournalistMapper.getInstance() ;


    public  static ConversationRequest getInstance () {
        if(instance == null )
            instance = new ConversationRequest();
        return  instance ;
    }

    @Override
    public Conversation DtoToEntity(ConversationRequest t) {
        return null;
    }

    @Override
    public ConversationResponse EntityToDto(Conversation conversation) {

        ConversationResponse conversationResponse = new ConversationResponse();
        conversationResponse.setId(conversation.getId());
        conversationResponse.setUser1(journalistMapper.EntityToSpecialDto(conversation.getUser1()));
        conversationResponse.setUser2(journalistMapper.EntityToSpecialDto(conversation.getUser2()));
        Project projectResponse = new Project();
        projectResponse.setId(conversation.getProject().getId());
        projectResponse.setName(conversation.getProject().getName());
        conversationResponse.setProject(projectResponse);
        conversationResponse.setTachName(conversation.getTach().getName());
        conversationResponse.setLastDateMessage(conversation.getLastMessageDate());
        return conversationResponse ;
    }

    public ConversationResponse EntityToSpecialDto(Conversation conversation) {

        ConversationResponse conversationResponse = new ConversationResponse();
        conversationResponse.setId(conversation.getId());
        conversationResponse.setUser1(journalistMapper.EntityToSpecialDto(conversation.getUser1()) );
        conversationResponse.setUser2(journalistMapper.EntityToSpecialDto(conversation.getUser2()));
        Project projectResponse = new Project();
        projectResponse.setId(conversation.getProject().getId());
        projectResponse.setName(conversation.getProject().getName());
        conversationResponse.setProject(projectResponse);
        conversationResponse.setTachName(conversation.getTach().getName());
        conversationResponse.setMessages(conversation.getMessages());
        conversationResponse.setLastDateMessage(conversation.getLastMessageDate());
        return conversationResponse ;
    }
}