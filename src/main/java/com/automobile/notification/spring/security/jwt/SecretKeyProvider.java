package com.automobile.notification.spring.security.jwt;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("secretKeyProvider")
public class SecretKeyProvider {
    public byte[] getKey() throws URISyntaxException, IOException {
        //return Files.readAllBytes(Paths.get(this.getClass().getResource("/jwt.key").toURI()));
    	return new String("tester").getBytes();
    }
}