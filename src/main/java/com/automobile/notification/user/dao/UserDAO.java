package com.automobile.notification.user.dao;

import java.util.List;

import com.automobile.notification.user.domain.UserEntity;

public interface UserDAO {
	UserEntity create(UserEntity userEntity)  throws Exception;

	UserEntity update(UserEntity userEntity);

	UserEntity getUserByUserName(String userName);

	List<UserEntity> getUser();

	public String delete(String userName);
}
