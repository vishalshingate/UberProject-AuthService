package com.example.uberprojectauthservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.security.KeyRep.Type.SECRET;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String secret;

    // this method creates the brand new jwt token based on the payload
    private String createToken(Map<String, Object> payload, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);
        return Jwts.builder()
            .claims(payload)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(expiryDate)
            .subject(email)
            .signWith(getSignKey())
            .compact();

    }

    private Claims extractAllPayload(String token) {

        return Jwts.parser().
            setSigningKey(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

    }

    private Key getSignKey()
    {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayload(token);
        return claimsResolver.apply(claims);
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * This method  checks if the token expiry before the current timestamp or not ?
     * default expiration is 10 hours
     * @param token JWT token
     * @return true if token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validate the token based upon subject which is email in our case, this token is assigned to this user or not
     * and check if token is not expired
     *
     * @param token
     * @param email
     * @return
     */
    private boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = extractEmail(token);
        return (userEmailFetchedFromToken.equals(email)) && (!isTokenExpired(token));

    }

    private String extractPhoneNumber(String token) {
        Claims claims = extractAllPayload(token);
        return claims.get("phoneNumber").toString();
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("username", "admin");
        payload.put("password", "admin");
        payload.put("email", "vishal@gmail.com");
        payload.put("role", "USER");
        payload.put("enabled", true);
        payload.put("phoneNumber", 234567787);
        String token = createToken(payload, "vishal@gmail.com");
        System.out.println(token);

        System.out.println(extractEmail(token));
        System.out.println(extractPhoneNumber(token));
    }
}
