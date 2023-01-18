package com.pfe.projectsmanagements.services.Auth;

import com.pfe.projectsmanagements.Dto.Auth.request.AuthRequestDto;
import com.pfe.projectsmanagements.Dto.Auth.request.LogOutRequest;
import com.pfe.projectsmanagements.Dto.Auth.request.RefreshTokenRequest;
import com.pfe.projectsmanagements.Dto.Auth.response.AuthResponseDto;
import com.pfe.projectsmanagements.Dto.Auth.response.MessageResponse;
import com.pfe.projectsmanagements.Dto.Auth.response.RefreshTokenResponse;
import com.pfe.projectsmanagements.Dto.Auth.response.UpdateAuthResponse;
import org.springframework.security.authentication.AuthenticationManager;


public interface AuthService {

    public AuthResponseDto Login(AuthenticationManager authenticationManager , AuthRequestDto authRequestDto);

    RefreshTokenResponse getTokenFromRefreshToken(RefreshTokenRequest request);

    public UpdateAuthResponse updatePassword(Long journalistId , String newPassword , String  confirmPassword) ;

    MessageResponse logout(LogOutRequest request);

}
