package com.pfe.projectsmanagements.Dto.Journalist.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalistResponseDto {
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
    private Set<String> functions = new HashSet<>();
    private Set<String> oldPics = new HashSet<>();
    private Set<String> roles = new HashSet<>();
}
