package com.pfe.projectsmanagements.Dto.Message.response.Others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectMessage {
    private Long id ;
    private String name ;
}
