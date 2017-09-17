package com.automobile.notification.user.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.spring.security.jwt.JWTService;
import com.automobile.notification.user.model.UserProfile;
import com.automobile.notification.user.service.LoginService;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

  @Autowired
   private LoginService loginService;
  
  @Autowired 
  private JWTService jwtService;


    @PostMapping
    public UserProfile login(@RequestBody UserProfile credentials,
                                HttpServletResponse response) {
        return loginService.login(credentials)
                .map(UserProfile -> {
                    try {
                        response.setHeader("Token", jwtService.tokenFor(UserProfile));
                    } catch (Exception e) {
                    	e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    return UserProfile;
                })
                .orElseThrow(() -> new RuntimeException(credentials.getUserName()));
    }
}