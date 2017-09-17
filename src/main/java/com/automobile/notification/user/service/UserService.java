package com.automobile.notification.user.service;

import com.automobile.notification.user.model.User;
import com.automobile.notification.user.model.UserProfile;

public interface UserService {
	   void save(User user,UserProfile loggedInUser)  throws Exception;

	   UserProfile login(String username);
	   
	   User checkUserName(String username);
}
