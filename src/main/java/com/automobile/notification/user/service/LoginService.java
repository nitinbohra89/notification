package com.automobile.notification.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.automobile.notification.user.model.UserProfile;

@Component
@Service("loginService")
public class LoginService {
	
	@Autowired
	 private UserService userService;

	    public Optional<UserProfile> login(UserProfile credentials) {
	    	UserProfile userProfile= userService.login(credentials.getUserName());
	    	if(userProfile.getPassword().equals(credentials.getPassword())){
	    		return Optional.of(userProfile);
	    	}else {
	    		credentials.setErrors(new Error("Invalid Username or Password"));
	    		return  Optional.of(credentials);
	    	}
	    }
}
