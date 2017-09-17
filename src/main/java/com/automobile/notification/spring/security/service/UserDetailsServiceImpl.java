package com.automobile.notification.spring.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.automobile.notification.user.model.UserProfile;
import com.automobile.notification.user.service.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserService userService;
	
	 @Override
	    @Transactional(readOnly = true)
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        UserProfile user = userService.login(username);

	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
	        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
	    }
}
