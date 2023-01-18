package com.pfe.projectsmanagements.Dto.Journalist.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JournalistInfo {
    private Long id ;
    private String firstName ;
    private String secondName ;
    private String userName ;
    private String email ;
    private String gender ;
    private String photo ;
    private String country ;
    private String city ;
    private Date birthday ;
}
