package com.pfe.projectsmanagements.entities;

import com.pfe.projectsmanagements.Enums.MessageStatus;
import com.pfe.projectsmanagements.entities.others.MessagePayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document("message")
public class Conversation {

    @Transient
    public static final String SEQUENCE_NAME = "message_sequence";
    private Long id ;
    @DBRef(lazy = true)
    private Tach tach ;
    @DBRef(lazy = true)
    private Project project ;
    @DBRef(lazy = true)
    private Journalist user1 ;
    @DBRef(lazy = true)
    private Journalist user2 ;

    List<MessagePayload> messages = new ArrayList<MessagePayload>();

    private Date lastMessageDate ;

    /*private Long id ;
    private Journalist sender ;
    private Journalist receiver ;
    private Tach tach ;
    private Project project ;
    private String body ;
    private String fileName ;
    private Date sendingDate ;
    private MessageStatus messageStatus ;

     */

}
