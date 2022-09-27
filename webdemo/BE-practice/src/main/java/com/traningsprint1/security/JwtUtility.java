package com.traningsprint1.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Component
public class JwtUtility implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtility.class);
    private static final String jwtSecret = "TuanPA-TRAINING";

    /** This generateJwtToken is to generate a token with the given param username
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + (100 * 60 * 60 * 24)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /** This getUserNameFromJwtToken is to get username from token
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /** This validateJwtToken is to validate token
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getSubject();
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
