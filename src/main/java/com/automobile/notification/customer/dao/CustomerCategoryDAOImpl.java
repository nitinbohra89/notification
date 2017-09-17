package com.automobile.notification.customer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.automobile.notification.customer.domain.CustomerCategoryEntity;
import com.automobile.notification.customer.exception.CustomerCategoryException;
import com.automobile.notification.utility.DBUtility;

@Repository("customerCategoryDAO")
@Lazy
@Scope("singleton")
public class CustomerCategoryDAOImpl implements CustomerCategoryDAO {
	private static final String CREATE_CUSTCAT_SQL = "INSERT INTO CUSTOMER_CATEGORY " + "(category_name,create_ts,created_by)" + "VALUES(?,?,?)";
	private static final String GET_CUSTCAT_BY_NAME_SQL="SELECT * FROM CUSTOMER_CATEGORY WHERE category_name=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public CustomerCategoryEntity create(CustomerCategoryEntity custCatEntity) throws CustomerCategoryException {
		try{
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdt.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(CREATE_CUSTCAT_SQL, new String[] { "cust_category_id" });
				pst.setString(1, custCatEntity.getCategoryName());
				pst.setTimestamp(2,new Timestamp(new Date().getTime()));
				pst.setString(3,"system");
				return pst;
			}
		}, keyHolder);
		custCatEntity.setCustomerCategoryId(keyHolder.getKey().longValue());
		return custCatEntity;
		}catch(Exception e){
			throw new CustomerCategoryException("Error in creating Customer Category.");
		}
	}

	@Override
	public CustomerCategoryEntity getCustomerCategoryByName(String customerCategoryName) throws CustomerCategoryException{
		try{JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		CustomerCategoryEntity customerCategory = (CustomerCategoryEntity) jdt.queryForObject(GET_CUSTCAT_BY_NAME_SQL,
				new Object[] { customerCategoryName }, new CustomerCategoryMapper());
		return customerCategory;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerCategoryException("Error in fetching Customer Category.");
		}
	}

}

class CustomerCategoryMapper implements RowMapper<CustomerCategoryEntity> {

	@Override
	public CustomerCategoryEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		CustomerCategoryEntity customerCategory = new CustomerCategoryEntity();
		customerCategory.setCategoryName(rs.getString("category_name"));
		customerCategory.setCustomerCategoryId(rs.getLong("cust_category_id"));
		customerCategory.setCreateTimestamp(rs.getTimestamp("create_ts"));
		return customerCategory;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
