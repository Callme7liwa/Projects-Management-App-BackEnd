package com.pfe.projectsmanagements.Dto.Journalist.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalistTachRequest {
    private Long journalistId ;
    private String tachName ;
}
