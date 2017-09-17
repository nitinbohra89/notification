package com.automobile.notification.spring.security.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}