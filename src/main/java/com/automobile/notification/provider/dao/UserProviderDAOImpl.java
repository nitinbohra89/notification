package com.automobile.notification.provider.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.automobile.notification.utility.DBUtility;

@Repository("userProviderDAO")
@Lazy
@Scope("singleton")
public class UserProviderDAOImpl implements UserProviderDAO{
/*	private static final String CREATE_USER_PROVIDER_SQL = "INSERT INTO USER_PROVIDER "
			+ "(username,provider_id,is_active,message,provider_key,provider_username,password)" + "VALUES(?,?,?,?,?,?,?)";
	private static final String UPDATE_USER_PROVIDER_SQL = "UPDATE USER_PROVIDER SET "
			+ "is_active=?,message=?,provider_key=?,provider_username=?,password=?,update_ts=? WHERE username=? AND provider_id=?";
	private static final String GET_USER_PROVIDER_SQL = "SELECT * FROM USER_PROVIDER";
	private static final String GET_USER_PROVIDER_BY_ID_SQL = "SELECT * FROM USER_PROVIDER WHERE provider_id=?";
	private static final String GET_USER_PROVIDER_BY_USER_SQL = "SELECT * FROM USER_PROVIDER WHERE username=?";
	private static final String DELETE_USER_PROVIDER_SQL = "DELETE FROM USER_PROVIDER WHERE provider_id=?";

	@Autowired(required = true)
	private DBUtility dbUtility;
	
	@Override
	public UserProviderEntity create(UserProviderEntity upEntity) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int rowCount = jdt.update(CREATE_USER_PROVIDER_SQL,
				new Object[] {upEntity.getUserName(),
						upEntity.getProviderId(),
						upEntity.getIsActive(),
						upEntity.getMessage(),
						upEntity.getProviderKey(),
						upEntity.getProviderUsername(),
						upEntity.getPassword()});

		if (rowCount == 0)
			return null;
		else
			return upEntity;
	}

	@Override
	public UserProviderEntity update(UserProviderEntity upEntity) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int rowCount = jdt.update(UPDATE_USER_PROVIDER_SQL,
				new Object[] {upEntity.getIsActive(),
						upEntity.getMessage(),
						upEntity.getProviderKey(),
						upEntity.getProviderUsername(),
						upEntity.getPassword(),
						new Timestamp(new Date().getTime()),
						upEntity.getUserName(),
						upEntity.getProviderId(),});

		if (rowCount == 0)
			return null;
		else
			return upEntity;
	}

	@Override
	public List<UserProviderEntity> getUserProviderByProviderId(long providerId) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		List<UserProviderEntity> providers = jdt.query(GET_USER_PROVIDER_BY_ID_SQL,new Object[] {providerId}, new BeanPropertyRowMapper(UserProviderEntity.class));
		return providers;
	}

	@Override
	public List<UserProviderEntity> getUserProviderByUserName(String userName) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		List<UserProviderEntity> providers = jdt.query(GET_USER_PROVIDER_BY_USER_SQL,new Object[] {userName}, new BeanPropertyRowMapper(UserProviderEntity.class));
		return providers;
	}

	@Override
	public List<UserProviderEntity> getUserProvider() {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		List<UserProviderEntity> providers = jdt.query(GET_USER_PROVIDER_SQL, new BeanPropertyRowMapper(UserProviderEntity.class));
		return providers;
	}

	@Override
	public Long delete(String username,long providerId) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int row = jdt.update(DELETE_USER_PROVIDER_SQL, new Object[] { username,providerId });
		if (row > 0)
			return providerId;
		else
			return new Long(0);
	}
*/
}
/*
class UserProviderMapper implements RowMapper<UserProviderEntity> {

	@Override
	public UserProviderEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		UserProviderEntity userProvider = new UserProviderEntity();
		userProvider.setUserName(rs.getString("username"));
		userProvider.setProviderId(rs.getLong("provider_id"));
		userProvider.setIsActive(rs.getString("is_active"));
		userProvider.setMessage(rs.getString("message"));
		userProvider.setPassword(rs.getString("password"));
		userProvider.setProviderKey(rs.getString("provider_key"));
		userProvider.setProviderUsername(rs.getString("provider_username"));
		userProvider.setCreateTimestamp(rs.getTimestamp("create_ts"));
		userProvider.setUpdateTimestamp(rs.getTimestamp("update_ts"));
		return userProvider;
	}
}*/
