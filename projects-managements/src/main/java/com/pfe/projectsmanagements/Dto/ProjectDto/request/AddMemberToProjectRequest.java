package com.pfe.projectsmanagements.Dto.ProjectDto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddMemberToProjectRequest {
    private Long journalistId ;
    private Long TeamId ;
}
