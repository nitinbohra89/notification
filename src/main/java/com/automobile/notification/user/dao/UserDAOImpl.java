package com.automobile.notification.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.automobile.notification.user.domain.UserEntity;
import com.automobile.notification.utility.DBUtility;

@Repository("userDAO")
@Lazy
@Scope("singleton")
public class UserDAOImpl implements UserDAO {

	private static final String CREATE_USER_SQL = "INSERT INTO USER " + "(username,password,role,store_id,created_by)"
			+ "VALUES(?,?,?,?,?)";
	private static final String UPDATE_USER_SQL = "UPDATE USER SET " + "password=?,updated_by=?,update_ts=? WHERE username=?";
	private static final String GET_USER_SQL = "SELECT * FROM USER";
	private static final String GET_USER_BY_ID_SQL = "SELECT * FROM USER WHERE username=?";
	private static final String DELETE_USER_SQL = "DELETE FROM USER WHERE username=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public UserEntity create(UserEntity userEntity) throws Exception {
		int rowCount =0;
		try{
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		 rowCount = jdt.update(CREATE_USER_SQL,
				new Object[] { userEntity.getUserName(), 
						userEntity.getPassword(),
						userEntity.getRole(),
						userEntity.getStoreId(),
						userEntity.getCreatedBy()});
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception("Database exception..");
		}
		if (rowCount == 0)
			return null;
		else
			return userEntity;
	}

	@Override
	public UserEntity update(UserEntity userEntity) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int rowCount = jdt.update(UPDATE_USER_SQL, new Object[] { userEntity.getPassword(),
				userEntity.getUpdatedBy(),
				new Timestamp(new Date().getTime()), userEntity.getUserName() });
		if (rowCount == 0)
			return null;
		else
			return userEntity;
	}

	@Override
	public UserEntity getUserByUserName(String userName) {
		try{
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		System.out.println("Username ---"+userName);
		UserEntity user = (UserEntity) jdt.queryForObject(GET_USER_BY_ID_SQL, new Object[] { userName },
				new UserMapper());
		System.out.println("Username ---"+user.getUserName());
		System.out.println("Username ---"+user.getPassword());
		System.out.println("Username ---"+user.getRole());
		System.out.println("Username ---"+user.getStoreId());

		return user;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<UserEntity> getUser() {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		List<UserEntity> users = jdt.query(GET_USER_SQL, new BeanPropertyRowMapper(UserEntity.class));
		return users;
	}

	@Override
	public String delete(String userName) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int row = jdt.update(DELETE_USER_SQL, new Object[] { userName });
		if (row > 0)
			return userName;
		else
			return null;
	}

}

class UserMapper implements RowMapper<UserEntity> {

	@Override
	public UserEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		if(rs==null){
			return null;
		}
		UserEntity user = new UserEntity();
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setRole(rs.getString("role"));
		user.setStoreId(rs.getLong("store_id"));
		user.setCreateTimestamp(rs.getTimestamp("create_ts"));
		user.setUpdateTimestamp(rs.getTimestamp("update_ts"));
		return user;
	}
}
