package com.pfe.projectsmanagements.Dto.ProjectDto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddListTachesToProjectRequest {

    private List<String> taches ;
}
