package com.pfe.projectsmanagements.services.Auth;

import com.pfe.projectsmanagements.Dto.Auth.request.AuthRequestDto;
import com.pfe.projectsmanagements.Dto.Auth.request.LogOutRequest;
import com.pfe.projectsmanagements.Dto.Auth.request.RefreshTokenRequest;
import com.pfe.projectsmanagements.Dto.Auth.response.AuthResponseDto;
import com.pfe.projectsmanagements.Dto.Auth.response.MessageResponse;
import com.pfe.projectsmanagements.Dto.Auth.response.RefreshTokenResponse;
import com.pfe.projectsmanagements.Dto.Auth.response.UpdateAuthResponse;
import com.pfe.projectsmanagements.dao.JournalistRepository;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.RefreshToken;
import com.pfe.projectsmanagements.exceptions.Auth.AuthenticationUnsuccesfulException;
import com.pfe.projectsmanagements.exceptions.Auth.PasswordDoesNotEqual;
import com.pfe.projectsmanagements.exceptions.Auth.RefreshTokenException;
import com.pfe.projectsmanagements.exceptions.Journalist.JournalistNotFoundException;
import com.pfe.projectsmanagements.mappers.JournalistMapper;
import com.pfe.projectsmanagements.security.JWTUtils;
import com.pfe.projectsmanagements.services.Journalist.JournalistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    private final  JournalistService journalistService ;
    private final  JWTUtils jwtUtils ;
    private static final Logger logger = LoggerFactory.getLogger(Journalist.class);
    private final JournalistMapper journalistMapper ;


    @Autowired
    private RefreshTokenService refreshTokenService ;

    @Autowired
    private JournalistRepository journalistRepository ;

    private BCryptPasswordEncoder passwordEncoder ;

    public AuthServiceImpl(JournalistService journalistService, JWTUtils jwtUtils, JournalistMapper journalistMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.journalistService = journalistService;
        this.jwtUtils = jwtUtils;
        this.journalistMapper = journalistMapper;
        this.passwordEncoder = bCryptPasswordEncoder ;
    }

    @Override
    public AuthResponseDto Login(AuthenticationManager authenticationManager, AuthRequestDto authRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUserName(), authRequestDto.getPassword()));
        if(Objects.nonNull(authentication))
        {
            logger.info("user is founded : {} ",authentication.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User userDetails = (User) authentication.getPrincipal();
            String accessToken = jwtUtils.generateJwtToken(authentication);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
            Journalist journalist = journalistService.getJournalistByUsername(userDetails.getUsername());

            return  AuthResponseDto
                    .builder()
                    .journalist(journalist)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken())
                    .build();

        }
        throw new AuthenticationUnsuccesfulException();

    }

    @Override
    public RefreshTokenResponse getTokenFromRefreshToken(RefreshTokenRequest request) {

        return refreshTokenService
                .findByToken(request.getRefreshToken())
                .map(RefreshToken::getJournalist)
                .map(journalist -> {
                    String token = jwtUtils.generateTokenAfterExpiration(journalist);
                    return RefreshTokenResponse.builder()
                            .accessToken(token)
                            .refreshToken(request.getRefreshToken())
                            .build();
                })
                .orElseThrow(()-> new RefreshTokenException(request.getRefreshToken(), "Refresh Token is not in data base !"));
    }

    @Override
    public UpdateAuthResponse updatePassword(Long journalistId, String newPassword, String confirmPassword) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(journalistId);
        if(journalistOptional.isPresent())
        {
            Journalist journalist = journalistOptional.get() ;
            if(newPassword.equals(confirmPassword))
            {
                journalist.setPassword(passwordEncoder.encode(newPassword));
                journalistRepository.save(journalist) ;
                return UpdateAuthResponse.builder().journalist(journalist).accessToken(UUID.randomUUID().toString()).build();
            }
            throw new PasswordDoesNotEqual() ;
        }
        throw new JournalistNotFoundException();
    }


    @Override
    public MessageResponse logout(LogOutRequest request) {
        refreshTokenService.deleteByJournalistId(request.getUserId());
        return new MessageResponse("Log Out successful !!");
    }

}
