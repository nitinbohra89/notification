package com.automobile.notification.spring.security.jwt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.automobile.notification.user.model.UserProfile;
import com.automobile.notification.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Qualifier("jwtService")
public class JWTService  {
    private static final String ISSUER = "in.sdqali.jwt";
   
    @Autowired
    private SecretKeyProvider secretKeyProvider;

    @Autowired
    UserService userService;
 
    public String tokenFor(UserProfile userProfile) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
       
        Date expiration = Date.from(LocalDateTime.now().plusHours(2).toInstant( ZoneOffset.UTC));
        String key= Jwts.builder()
                .setSubject(userProfile.getUserName())
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        System.out.println("JWT KEY--"+key);
        return key;
    }
    
    public Optional<UserProfile> verify(String token) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        UserProfile user = userService.login(claims.getBody().getSubject().toString());
        return Optional.of(user);
    }
}