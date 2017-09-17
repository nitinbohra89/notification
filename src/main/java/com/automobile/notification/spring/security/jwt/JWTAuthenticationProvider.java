package com.automobile.notification.spring.security.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.automobile.notification.user.model.UserProfile;

@Component
@Qualifier("jwtAuthenticationProvider")
public class JWTAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private JWTService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication){
        try {
            Optional<UserProfile> possibleProfile = jwtService.verify((String) authentication.getCredentials());
            return new JWTAuthenticatedProfile(possibleProfile.get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthToken.class.equals(authentication);
    }
}