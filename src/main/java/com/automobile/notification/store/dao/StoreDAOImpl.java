package com.automobile.notification.store.dao;

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

import com.automobile.notification.store.domain.StoreEntity;
import com.automobile.notification.store.exception.StoreException;
import com.automobile.notification.utility.DBUtility;

@Repository("storeDAO")
@Lazy
@Scope("singleton")
public class StoreDAOImpl implements StoreDAO {
	private static final String CREATE_STORE_SQL = "INSERT INTO STORE "
			+ "(store_id,store_name,address,city,zipcode,state_name,phone,mobile,create_ts,created_by)"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_STORE_SQL = "UPDATE STORE SET "
			+ "store_name=?,address=?,city=?,zipcode=?,state_name=?,phone=?,mobile=?,update_ts=? WHERE store_id=?";
	private static final String GET_STORE_BY_ID_SQL = "SELECT * FROM STORE WHERE store_id=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public StoreEntity create(StoreEntity storeEntity) throws StoreException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();

			jdt.update(CREATE_STORE_SQL,
					new Object[] { storeEntity.getStoreId(), storeEntity.getStoreName(), storeEntity.getAddress(),
							storeEntity.getCity(), storeEntity.getZipcode(), storeEntity.getStateName(),
							storeEntity.getPhone(), storeEntity.getMobile(), new Timestamp(new Date().getTime()),
							"system" });
			return storeEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new StoreException("Error in creating Store.");
		}

	}

	@Override
	public StoreEntity update(StoreEntity storeEntity) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int rowCount = jdt.update(UPDATE_STORE_SQL,
				new Object[] { storeEntity.getStoreName(), storeEntity.getAddress(), storeEntity.getCity(),
						storeEntity.getZipcode(), storeEntity.getStateName(), storeEntity.getPhone(),
						storeEntity.getMobile(), new Timestamp(new Date().getTime()), storeEntity.getStoreId() });

		if (rowCount == 0)
			return null;
		else
			return storeEntity;
	}

	@Override
	public StoreEntity getStoreById(long storeId) throws StoreException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			StoreEntity store = (StoreEntity) jdt.queryForObject(GET_STORE_BY_ID_SQL, new Object[] { storeId },
					new StoreMapper());
			return store;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new StoreException("Error in fetching Store Details.");
		}
	}

}

class StoreMapper implements RowMapper<StoreEntity> {

	@Override
	public StoreEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		StoreEntity store = new StoreEntity();
		store.setStoreId(rs.getLong("store_id"));
		store.setStoreName(rs.getString("store_name"));
		store.setAddress(rs.getString("address"));
		store.setCity(rs.getString("city"));
		store.setStateName(rs.getString("state_name"));
		store.setMobile(rs.getString("mobile"));
		store.setPhone(rs.getString("phone"));
		store.setZipcode(rs.getString("zipcode"));
		store.setCreateTimestamp(rs.getTimestamp("create_ts"));
		store.setUpdateTimestamp(rs.getTimestamp("update_ts"));
		return store;
	}
}