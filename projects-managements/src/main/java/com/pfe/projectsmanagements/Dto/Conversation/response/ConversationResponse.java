package com.pfe.projectsmanagements.Dto.Conversation.response;

import com.pfe.projectsmanagements.Dto.Conversation.others.Project;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistInfo;
import com.pfe.projectsmanagements.entities.others.MessagePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponse {
    private Long id ;
    private Project project ;
    private String tachName ;
    private JournalistInfo user1 ;
    private JournalistInfo user2 ;
    private List<MessagePayload> messages  = new ArrayList<>();
    private Date lastDateMessage ;
}
