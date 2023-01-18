package com.pfe.projectsmanagements.services.Message;


import com.pfe.projectsmanagements.Dto.Conversation.request.ConversationRequest;
import com.pfe.projectsmanagements.Dto.Conversation.response.ConversationResponse;
import com.pfe.projectsmanagements.Dto.Message.request.AddMessageToConversationRequest;
import com.pfe.projectsmanagements.Dto.Message.response.MessageResponse;
import com.pfe.projectsmanagements.entities.Conversation;
import com.pfe.projectsmanagements.entities.others.MessagePayload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {

    public ConversationResponse newConversation(ConversationRequest request) ;

    public MessagePayload addMessageToConversation(Long journalist , MultipartFile file , String body , Long senderId , Long receiverId);


    List<ConversationResponse> getConversations();

    List<ConversationResponse> getMessageOfUser(Long userId);

    public boolean changeStatusOfMessage(Long conversationId , Long userIdAuthenticated);

    MessagePayload addMessageToConversationWithoutFile(Long conversationId, String body, Long sender, Long receiver);
}