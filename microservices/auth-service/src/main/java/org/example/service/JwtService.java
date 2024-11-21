package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.dto.AuthenticationDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String jwtSecret="f09269605c654c91d6b632c38d52fc7606a52729528b9c54c91995428c782e2f";
    private static final long token_validity=10*60*60*1000;

    public String generateJwtToken(AuthenticationDto authUset) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(authUset.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+token_validity))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    public void validateJwtToken(String token) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }

    public String getUsernameFromJwtToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }



}
