package com.pfe.projectsmanagements.security;


import com.pfe.projectsmanagements.entities.Journalist;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    public String generateJwtToken(Authentication authentication) {
        User springUser = (User) authentication.getPrincipal();
        return
                Jwts.builder()
                        .setSubject(springUser.getUsername())
                        .setExpiration(
                                new Date(
                                        System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME
                                )
                        )
                        .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                        .claim("roles", springUser.getAuthorities())
                        .compact();

       /* String jwtRefreshToken=Jwts.builder().setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ 15*60*100)
                        .setI */

    }

    public String generateTokenAfterExpiration(Journalist user)
    {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME
                        )
                )
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                .claim("roles", user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList()))
                .compact();
    }

   /* public String generateTokenFromUsername(String username) {
        return
                Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }*/

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}