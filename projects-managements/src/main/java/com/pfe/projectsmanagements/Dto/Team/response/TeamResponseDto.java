package com.pfe.projectsmanagements.Dto.Team.response;

import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDto {
    private Long id ;
    private String name ;
    private String photo ;
    private Integer numberEtoiles ;
    private Date creationDate ;
    private List<JournalistResponseDto> journalistes = new ArrayList<>();
}
