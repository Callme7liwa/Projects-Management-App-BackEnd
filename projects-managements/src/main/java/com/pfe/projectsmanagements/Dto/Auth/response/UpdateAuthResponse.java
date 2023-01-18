package com.pfe.projectsmanagements.Dto.Auth.response;

import com.pfe.projectsmanagements.entities.Journalist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAuthResponse {
    private String accessToken   ;
    private Journalist journalist  ;
}
