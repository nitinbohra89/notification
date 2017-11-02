package com.automobile.notification.user.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.automobile.notification.user.dao.UserDAO;
import com.automobile.notification.user.domain.UserEntity;
import com.automobile.notification.user.model.UserProfile;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired(required=true)
	private UserDAO userDAO;
	
	private final HashMap<String,User> userMap=new HashMap<>();  
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDAO.getUserByUserName(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
        	User user=userEntityToUserMapper(userEntity);
            return user;

        }
        }

	public User userEntityToUserMapper(UserEntity userEntity) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        GrantedAuthority ga=new SimpleGrantedAuthority(userEntity.getRole());
        grantedAuthorities.add(ga);
		User user = new User(
						userEntity.getUserName(), 
						userEntity.getPassword(), 
						true, 
						true, 
						true,
						true, 
						grantedAuthorities);
		return user;
	}
	public UserEntity userToUserEntityMapper(User user,UserProfile loggedInUser) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(user.getUsername());
		userEntity.setPassword(user.getPassword());
		userEntity.setRole(user.getAuthorities().toString());
		userEntity.setStoreId(loggedInUser.getStoreId());
		userEntity.setCreatedBy(loggedInUser.getUserId());
		return userEntity;
	}
}
