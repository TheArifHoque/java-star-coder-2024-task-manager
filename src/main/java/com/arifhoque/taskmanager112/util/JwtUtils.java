package com.arifhoque.taskmanager112.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a component class for Jwt util. Here secret key and expiration is declared
 * @author Ariful Hoque
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.jwtExp}")
    private int JWT_EXP;

    /**
     * Json web token is generated in this method
     * @param userDetails
     * @return
     */
    private String generateToken(UserDetails userDetails) {
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("Authorities", authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date((new Date()).getTime()))
                .setExpiration(new Date((new Date()).getTime() + JWT_EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * This method is for validating token
     * @param token
     * @return
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException |
                 UnsupportedJwtException | IllegalArgumentException ex) {
            logger.error("Invalid Token!");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired Token!");
        }
        return false;
    }

    /**
     * This method is to extract username from token
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * This method is to extract the role/authorities of the user
     * @param token
     * @return
     */
    public List<String> extractAuthorities(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("Authorities", List.class);
    }
}
