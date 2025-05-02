package com.surya.user.management.svc.service;

import com.surya.user.management.svc.model.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

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
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey(){
//        byte[] key = new byte[32];
//        new SecureRandom().nextBytes(key);
//        String base64Key = Base64.getEncoder().encodeToString(key);
//
//        System.out.println("Secrete Key: " + base64Key);

        return secretKey = "KYe7czMc9CxG7NwnJz1uJNCv3JPrJA7PwvMXEXlnq1c=";
    }
}
