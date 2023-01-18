package com.pfe.projectsmanagements.services.Auth;

import com.pfe.projectsmanagements.dao.JournalistRepository;
import com.pfe.projectsmanagements.dao.RefreshTokenRepository;
import com.pfe.projectsmanagements.entities.RefreshToken;
import com.pfe.projectsmanagements.exceptions.Auth.RefreshTokenException;
import com.pfe.projectsmanagements.security.SecurityConstants;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository ;

    @Autowired
    private JournalistRepository journalistRepository ;

    @Autowired
    private SequenceGeneratorService service;

    public Optional<RefreshToken> findByToken(String token)
    {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String username)
    {
        RefreshToken refreshToken = new RefreshToken() ;
        refreshToken.setId(service.getSequenceNumber(RefreshToken.SEQUENCE_NAME));
        refreshToken.setJournalist(journalistRepository.findByUserName(username).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken ;
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken)
    {
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0)
        {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException(refreshToken.getToken() , "Refresh token was expired  , please make a new sign in !");
        }
        return refreshToken ;
    }

    @Transactional
    public int deleteByJournalistId(Long journalistId)
    {
        return refreshTokenRepository.deleteByJournalist(journalistRepository.findById(journalistId).get());
    }
}
