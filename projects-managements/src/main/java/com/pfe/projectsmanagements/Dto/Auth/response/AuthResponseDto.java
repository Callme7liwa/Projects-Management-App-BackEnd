package com.pfe.projectsmanagements.Dto.Auth.response;

import com.pfe.projectsmanagements.entities.Journalist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    private Journalist journalist ;
    private String accessToken ;
    private String refreshToken ;
}
