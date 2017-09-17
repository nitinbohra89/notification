package com.automobile.notification.customer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.automobile.notification.customer.domain.CustomerEntity;
import com.automobile.notification.customer.exception.CustomerException;
import com.automobile.notification.utility.DBUtility;

@Repository("customerDAO")
@Lazy
@Scope("singleton")
public class CustomerDAOImpl implements CustomerDAO {
	private static final String CREATE_CUSTOMER_SQL = "INSERT INTO CUSTOMER "
			+ "(customer_id,customer_name,address,city,zipcode,state_name,"
			+ "phone,mobile,email,cust_category_id,create_ts,created_by)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_CUSTOMER_BY_ID_SQL = "SELECT * FROM CUSTOMER WHERE customer_id=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public CustomerEntity create(CustomerEntity customerEntity) throws CustomerException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(CREATE_CUSTOMER_SQL,
					new Object[] { customerEntity.getCustomerId(), customerEntity.getCustomerName(),
							customerEntity.getAddress(), customerEntity.getCity(), customerEntity.getZipcode(),
							customerEntity.getStateName(), customerEntity.getPhone(), customerEntity.getMobile(),
							customerEntity.getEmail(), customerEntity.getCustomerCategoryId(),new Timestamp(new Date().getTime()), "system" });
			return customerEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Error in creating Dealer");
		}

	}

	@Override
	public CustomerEntity getCustomerById(long customerId) throws CustomerException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			CustomerEntity customer = (CustomerEntity) jdt.queryForObject(GET_CUSTOMER_BY_ID_SQL,
					new Object[] { customerId }, new CustomerMapper());
			return customer;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Error in fetching Customer Details.");
		}
	}

	class CustomerMapper implements RowMapper<CustomerEntity> {

		@Override
		public CustomerEntity mapRow(ResultSet rs, int rownum) throws SQLException {

			CustomerEntity customer = new CustomerEntity();
			customer.setCustomerId(rs.getLong("customer_id"));
			customer.setCustomerCategoryId(rs.getLong("cust_category_id"));
			customer.setCustomerName(rs.getString("customer_name"));
			customer.setAddress(rs.getString("address"));
			customer.setCity(rs.getString("city"));
			customer.setMobile(rs.getString("mobile"));
			customer.setPhone(rs.getString("phone"));
			customer.setStateName(rs.getString("state_name"));
			customer.setZipcode(rs.getString("zipcode"));
			customer.setCreateTimestamp(rs.getTimestamp("create_ts"));
			customer.setUpdateTimestamp(rs.getTimestamp("update_ts"));
			return customer;
		}
	}
}
