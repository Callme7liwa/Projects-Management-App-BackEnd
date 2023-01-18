package com.pfe.projectsmanagements.services.Auth;

import com.pfe.projectsmanagements.entities.RefreshToken;
import com.pfe.projectsmanagements.exceptions.Auth.RefreshTokenException;
import com.pfe.projectsmanagements.security.SecurityConstants;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(String username);


    public RefreshToken verifyExpiration(RefreshToken refreshToken);

    public int deleteByJournalistId(Long journalistId);
}
