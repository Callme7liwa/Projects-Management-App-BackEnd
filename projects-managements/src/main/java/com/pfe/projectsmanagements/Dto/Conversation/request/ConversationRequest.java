package com.pfe.projectsmanagements.Dto.Conversation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationRequest {
    private Long user1Id ;
    private Long user2Id ;
    private Long projectId ;
    private String tachName ;
}
