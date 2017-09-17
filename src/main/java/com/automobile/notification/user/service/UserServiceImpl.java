package com.automobile.notification.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.user.dao.UserDAO;
import com.automobile.notification.user.domain.UserEntity;
import com.automobile.notification.user.model.User;
import com.automobile.notification.user.model.UserProfile;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	
	public UserProfile login(String username) {
		UserEntity user = userDAO.getUserByUserName(username);
		if(user!=null)
			return UserEntityToLoggedInUserMapper(user);
		else 
			return null;
	}

	public void save(User user,UserProfile loggedInUser) throws Exception{
		UserEntity userEntity=UserToUserEntityMapper(user,loggedInUser);
		userDAO.create(userEntity);
	}
	
	
	public UserProfile UserEntityToLoggedInUserMapper(UserEntity userEntity) {
		UserProfile user = new UserProfile();
		user.setUserName(userEntity.getUserName());
		user.setPassword(userEntity.getPassword());
		user.setRole(userEntity.getRole());
		user.setStoreId(userEntity.getStoreId());
		return user;
	}
	public User UserEntityToUserMapper(UserEntity userEntity) {
		User user = new User();
		user.setUserName(userEntity.getUserName());
		user.setPassword(userEntity.getPassword());
		user.setRole(userEntity.getRole());
		user.setStoreId(userEntity.getStoreId());
		return user;
	}
	
	public UserEntity UserToUserEntityMapper(User user,UserProfile loggedInUser) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(user.getUserName());
		userEntity.setPassword(user.getPassword());
		userEntity.setRole(user.getRole());
		userEntity.setStoreId(loggedInUser.getStoreId());
		userEntity.setCreatedBy(loggedInUser.getUserId());
		return userEntity;
	}

	@Override
	public User checkUserName(String username) {
		UserEntity user = userDAO.getUserByUserName(username);
		if(user!=null)
			return UserEntityToUserMapper(user);
		else 
			return null;	}
}
