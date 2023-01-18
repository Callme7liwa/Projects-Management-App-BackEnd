package com.pfe.projectsmanagements.Dto.ProjectDto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {

    private String name ;
    private String resume ;
    private long chefId ;
    private Long teamId ;
    private List<String>  tachesNames ;

}
