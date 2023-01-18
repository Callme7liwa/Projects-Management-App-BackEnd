package com.pfe.projectsmanagements.Dto.Journalist.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalistRequestDto {

    private String firstName ;
    private String secondName ;
    private String userName ;
    private String email ;
    private String gender ;
    private String country ;
    private String city ;
    private Date birthday  ;
    private Set<String> functions = new HashSet<>();
}
