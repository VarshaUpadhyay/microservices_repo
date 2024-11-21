package org.example.util;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    private static final String jwtSecret="f09269605c654c91d6b632c38d52fc7606a52729528b9c54c91995428c782e2f";

    public void validateJwtToken(String token) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }





}
