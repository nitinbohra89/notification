package com.automobile.notification.user.restController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.spring.security.jwt.TokenHelper;
import com.automobile.notification.user.exception.InvalidOTPException;
import com.automobile.notification.user.model.ChangePasswordRequest;
import com.automobile.notification.user.model.LoginRequest;
import com.automobile.notification.user.model.LoginResponse;
import com.automobile.notification.user.model.ResetPasswordRequest;
import com.automobile.notification.user.service.UserService;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = { "*" }, maxAge = 4800, allowCredentials = "true")
public class LoginController {

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping(path = "/login", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody LoginRequest userRequest, HttpServletResponse response)
			throws AuthenticationException, IOException {
		try {
			// Perform the security
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));

			// Inject into security context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// token creation
			User user = (User) authentication.getPrincipal();
			String jws = tokenHelper.generateToken(user.getUsername());
			int expiresIn = tokenHelper.getExpiredIn();

			// Return the token
			return ResponseEntity.ok(new LoginResponse(user.getUsername(), jws, expiresIn, null, null));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new LoginResponse(userRequest.getUsername(), null, null, Integer.valueOf(101),
					"Username or Password is Invalid."));
		}
	}

	@PostMapping(path = "/changepassword", produces = "application/json")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest userRequest, HttpServletResponse response)
			throws AuthenticationException, IOException {
		try {
			User user = userService.changePassword(userRequest);
			// token creation
			String jws = tokenHelper.generateToken(user.getUsername());
			int expiresIn = tokenHelper.getExpiredIn();
			return ResponseEntity.ok(new LoginResponse(user.getUsername(), jws, expiresIn, null, null));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new LoginResponse(userRequest.getUsername(), null, null, Integer.valueOf(101),
					"Current Password is Invalid."));
		} catch (UsernameNotFoundException e) {
			return ResponseEntity
					.ok(new LoginResponse(userRequest.getUsername(), null, null, Integer.valueOf(101), e.getMessage()));
		}
	}

	@PostMapping(path = "/resetpassword", produces = "application/json")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest userRequest, HttpServletResponse response)
			throws AuthenticationException, IOException {
		try {
			User user = userService.resetPassword(userRequest);
			return ResponseEntity.ok(new LoginResponse(user.getUsername(), null, null, null, null));
		} catch (InvalidOTPException e) {
			return ResponseEntity.ok(new LoginResponse(userRequest.getUsername(), null, null, Integer.valueOf(101),
					e.getMessage()));
		} catch (UsernameNotFoundException e) {
			return ResponseEntity
					.ok(new LoginResponse(userRequest.getUsername(), null, null, Integer.valueOf(101), e.getMessage()));
		}
	}
	
	@PostMapping(path = "/resetpassword/otp", produces = "application/json")
	public ResponseEntity<?> sendOTP(@RequestBody LoginRequest userRequest, HttpServletResponse response)
			throws AuthenticationException, IOException {
		try {
			userService.sendOTP(userRequest.getUsername());
			return ResponseEntity
					.ok(new LoginResponse(userRequest.getUsername(), null, null,null,null));
		} catch (UsernameNotFoundException e) {
			return ResponseEntity
					.ok(new LoginResponse(userRequest.getUsername(), null, null, Integer.valueOf(101), e.getMessage()));
		}
	}
}