package com.pfe.projectsmanagements.Dto.ProjectDto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskResp {
    private String name;
    private String tachStatus;
    private Long journalistId ;
    private String fileName ;
}
