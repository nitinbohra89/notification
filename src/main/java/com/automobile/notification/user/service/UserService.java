package com.automobile.notification.user.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.automobile.notification.user.dao.UserDAO;
import com.automobile.notification.user.domain.UserEntity;
import com.automobile.notification.user.exception.InvalidOTPException;
import com.automobile.notification.user.model.ChangePasswordRequest;
import com.automobile.notification.user.model.ResetPasswordRequest;
import com.automobile.notification.utility.GenerateOTPUtility;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired(required = true)
	private UserDAO userDAO;

	private final HashMap<String, User> userMap = new HashMap<>();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userDAO.getUserByUserName(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			User user = userEntityToUserMapper(userEntity);
			return user;

		}
	}

	public User changePassword(ChangePasswordRequest request)
			throws UsernameNotFoundException, BadCredentialsException {
		UserEntity userEntity = userDAO.getUserByUserName(request.getUsername());
		if (userEntity == null) {
			throw new UsernameNotFoundException("User does not exist.");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (encoder.matches(request.getCurrentPassword(), userEntity.getPassword())) {
			userEntity.setPassword(encoder.encode(request.getNewPassword()));
			userEntity = userDAO.update(userEntity);
			return userEntityToUserMapper(userEntity);
		} else {
			throw new BadCredentialsException("Current Password is not valid.");
		}
	}

	public User resetPassword(ResetPasswordRequest request) throws UsernameNotFoundException, InvalidOTPException {
		UserEntity userEntity = userDAO.getUserByUserName(request.getUsername());
		if (userEntity == null) {
			throw new UsernameNotFoundException("User does not exist.");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (request.getMobileOTP().equals(userEntity.getMobileOTP())
				&& request.getEmailOTP().equals(userEntity.getEmailOTP())) {
			userEntity.setPassword(encoder.encode(request.getNewPassword()));
			userEntity.setMobileOTP(null);
			userEntity.setEmailOTP(null);
			userEntity = userDAO.update(userEntity);
			return userEntityToUserMapper(userEntity);
		} else {
			throw new InvalidOTPException();
		}
	}

	public void sendOTP(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userDAO.getUserByUserName(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User does not exist.");
		}
		String mobileOTP = GenerateOTPUtility.generateOTP();
		// sendMobileOTP()
		String emailOTP = GenerateOTPUtility.generateOTP();
		// sendEMailOTP()
		userEntity.setMobileOTP(mobileOTP);
		userEntity.setEmailOTP(emailOTP);
		userDAO.update(userEntity);

	}

	public User userEntityToUserMapper(UserEntity userEntity) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		GrantedAuthority ga = new SimpleGrantedAuthority(userEntity.getRole());
		grantedAuthorities.add(ga);
		User user = new User(userEntity.getUserName(), userEntity.getPassword(), true, true, true, true,
				grantedAuthorities);
		return user;
	}

}
