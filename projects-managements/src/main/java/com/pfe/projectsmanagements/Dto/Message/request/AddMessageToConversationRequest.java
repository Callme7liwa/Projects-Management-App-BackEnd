package com.pfe.projectsmanagements.Dto.Message.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMessageToConversationRequest {
    private String body ;
    private String file ;
}
