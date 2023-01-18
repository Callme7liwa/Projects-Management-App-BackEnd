package com.pfe.projectsmanagements.Dto.Images;

import com.pfe.projectsmanagements.entities.Journalist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {
    private Journalist journalist ;
    private String message;

}