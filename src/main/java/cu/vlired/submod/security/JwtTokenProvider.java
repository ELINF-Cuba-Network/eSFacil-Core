/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.security;

import cu.vlired.submod.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * @author luizo
 */

@Component
public class JwtTokenProvider {
    
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        UserData userDetails = (UserData) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            throw new TokenExpiredException("La firma de autenticación no es válida ¿Eres quien dices ser?");
        } catch (MalformedJwtException ex) {
            throw new TokenExpiredException("La firma de autenticación no es válida ¿Eres quien dices ser?");
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException("Su session ha expirado");
        } catch (UnsupportedJwtException ex) {
            throw new TokenExpiredException("Sus credenciales no son válidas ¿Eres quien dices ser?");
        } catch (IllegalArgumentException ex) {
            throw new TokenExpiredException("Sus credenciales no son válidas ¿Eres quien dices ser?");
        }
    }
}
