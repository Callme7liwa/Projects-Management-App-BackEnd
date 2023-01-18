package com.pfe.projectsmanagements.entities.others;

import com.pfe.projectsmanagements.Enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessagePayload {
    private Long senderId  ;
    private Long receiverId ;
    private String body ;
    private String fileName ;
    private Date sendingDate ;
    private MessageStatus messageStatus ;
}
