package com.pfe.projectsmanagements.controllers.Auth;

import com.pfe.projectsmanagements.Dto.Auth.request.AuthRequestDto;
import com.pfe.projectsmanagements.Dto.Auth.request.RefreshTokenRequest;
import com.pfe.projectsmanagements.Dto.Auth.response.AuthResponseDto;
import com.pfe.projectsmanagements.Dto.Auth.response.RefreshTokenResponse;
import com.pfe.projectsmanagements.Dto.Auth.response.UpdateAuthResponse;
import com.pfe.projectsmanagements.services.Auth.AuthService;
import com.pfe.projectsmanagements.services.Auth.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("auth/")
public class AuthRestController {

    private AuthService authService;

    private AuthenticationManager authenticationManager ;


    @Autowired
    public AuthRestController(AuthService authService , AuthenticationManager authenticationManager) {
        this.authService = authService ;
        this.authenticationManager = authenticationManager ;
    }


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto)
    {
        System.out.println("ok => " + authRequestDto);
        AuthResponseDto authResponseDto = authService.Login(authenticationManager,authRequestDto);
        if(Objects.nonNull(authResponseDto))
            return new ResponseEntity<>(authResponseDto,HttpStatus.OK);
        return new ResponseEntity<>("Unsuccesful Authentication" , HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request)
    {
        String requestRefreshToken = request.getRefreshToken();

        RefreshTokenResponse refreshTokenResponse = authService.getTokenFromRefreshToken(request);

        return new ResponseEntity<>(refreshTokenResponse , HttpStatus.OK);

    }

    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<UpdateAuthResponse> updatePassword(@PathVariable("id") Long journalistId , @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword)
    {
        UpdateAuthResponse response = authService.updatePassword(journalistId , newPassword , confirmPassword);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }



}
