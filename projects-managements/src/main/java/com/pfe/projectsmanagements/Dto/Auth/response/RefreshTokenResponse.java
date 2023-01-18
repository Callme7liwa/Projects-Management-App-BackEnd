package com.pfe.projectsmanagements.Dto.Auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenResponse {
    private String accessToken ;
    private String refreshToken ;
    private String tokenType = "Bearer";
}
