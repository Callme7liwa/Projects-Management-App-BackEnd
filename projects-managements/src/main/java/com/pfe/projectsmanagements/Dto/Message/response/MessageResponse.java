package com.pfe.projectsmanagements.Dto.Message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private String body ;
    private String fileName ;
    private Date sendingDate ;

}
