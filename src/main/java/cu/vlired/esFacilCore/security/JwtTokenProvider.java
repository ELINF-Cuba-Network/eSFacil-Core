package cu.vlired.esFacilCore.security;

import cu.vlired.esFacilCore.exception.TokenExpiredException;
import cu.vlired.esFacilCore.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    final
    MessageSource messageSource;

    public JwtTokenProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String generateToken(Authentication authentication) {

        User userDetails = (User) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts
                .builder()
                .setSubject(userDetails.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public UUID getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (SignatureException | MalformedJwtException ex) {
            throw new TokenExpiredException(messageSource.getMessage("app.security.the.authentication.signature.is.not.valid.are.you.who.you.say.you.are", null, LocaleContextHolder.getLocale()));
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException(messageSource.getMessage("app.security.your.session.has.expired", null, LocaleContextHolder.getLocale()));
        } catch (UnsupportedJwtException | IllegalArgumentException ex) {
            throw new TokenExpiredException(messageSource.getMessage("app.security.your.credentials.are.not.valid.are.you.who.you.say.you.are", null, LocaleContextHolder.getLocale()));
        }
    }
}
