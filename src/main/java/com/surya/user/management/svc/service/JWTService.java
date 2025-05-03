package com.surya.user.management.svc.service;

import com.surya.user.management.svc.model.LoginRequest;
import com.surya.user.management.svc.utils.UserUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    public final Logger logger = LoggerFactory.getLogger(JWTService.class);

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    public String generateJwtToken(@Valid LoginRequest request) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("emailId", request.getEmailId());
        claims.put("role", request.getRole().toString());

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(request.getEmailId())
                .issuer("Surya")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*10*1000))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String jwtToken) {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaims(String jwtToken, Function<Claims,T> claimsResolver) {
        Claims claims = extractClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String jwtToken) {
       return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwtToken).getPayload();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        return isTokenExpired(jwtToken) && isValidRole(jwtToken, userDetails);
    }

    private boolean isValidRole(String jwtToken, UserDetails userDetails) {
        Optional<String> role = UserUtils.getRole(userDetails.getAuthorities());
        String roleExtracted = extractRole(jwtToken);
        logger.info("User Detail Role: {} Role Extracted from token: {}", role, roleExtracted);
        return role.isPresent() && role.get().equalsIgnoreCase(roleExtracted);
    }

    private String extractRole(String jwtToken) {
        return extractClaims(jwtToken, c ->  c.get("role", String.class));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).after(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration);
    }

//    public String getSecretKey(){
//        byte[] key = new byte[32];
//        new SecureRandom().nextBytes(key);
//        String base64Key = Base64.getEncoder().encodeToString(key);
//
//        System.out.println("Secrete Key: " + base64Key);
//
//        return secretKey = "KYe7czMc9CxG7NwnJz1uJNCv3JPrJA7PwvMXEXlnq1c=";
//    }
}
