package com.automobile.notification.dealer.dao;

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

import com.automobile.notification.dealer.domain.DealerEntity;
import com.automobile.notification.dealer.exception.DealerException;
import com.automobile.notification.utility.DBUtility;

@Repository("dealerDAO")
@Lazy
@Scope("singleton")
public class DealerDAOImpl implements DealerDAO {
	private static final String CREATE_DEALER_SQL = "INSERT INTO DEALER "
			+ "(dealer_id,dealer_name,address,city,zipcode,state_name,phone,mobile,create_ts,created_by)"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_DEALER_ID_SQL = "UPDATE DEALER SET "
			+ " dealer_id=?,update_ts=?,updated_by=? WHERE dealer_name=?";

	private static final String UPDATE_DEALER_NAME_SQL = "UPDATE DEALER SET "
			+ " dealer_name=?,update_ts=?,updated_by=? WHERE dealer_id=?";
	private static final String GET_DEALER_BY_NAME_OR_ID_SQL = "SELECT * FROM DEALER WHERE dealer_name=? OR dealer_id=?";
	
	@Autowired(required = true)
	private DBUtility dbUtility;

	public DealerEntity create(DealerEntity dealerEntity) throws DealerException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(CREATE_DEALER_SQL,
					new Object[] { dealerEntity.getDealerId(), dealerEntity.getDealerName(), dealerEntity.getAddress(),
							dealerEntity.getCity(), dealerEntity.getZipcode(), dealerEntity.getStateName(),
							dealerEntity.getPhone(), dealerEntity.getMobile(), new Timestamp(new Date().getTime()),
							"system" });
			return dealerEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DealerException("Error in creating Dealer");
		}
	}

	@Override
	public DealerEntity getDealerByNameOrId(String dealerName, String delaerId) throws DealerException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			DealerEntity dealer = (DealerEntity) jdt.queryForObject(GET_DEALER_BY_NAME_OR_ID_SQL,
					new Object[] { dealerName, delaerId }, new DealerMapper());
			return dealer;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DealerException("Error in fetching Dealer");
		}
	}

	@Override
	public int updateDealerId(String dealerId, String dealerName) throws DealerException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(UPDATE_DEALER_ID_SQL,
					new Object[] { dealerId, new Timestamp(new Date().getTime()), "system", dealerName });
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DealerException("Error in creating Dealer");
		}
	}

	@Override
	public int updateDealerName(String dealerId, String dealerName) throws DealerException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(UPDATE_DEALER_NAME_SQL,
					new Object[] { dealerName, new Timestamp(new Date().getTime()), "system", dealerId });
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DealerException("Error in creating Dealer");
		}
	}
}

class DealerMapper implements RowMapper<DealerEntity> {

	@Override
	public DealerEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		DealerEntity dealer = new DealerEntity();
		dealer.setDealerId(rs.getString("dealer_id"));
		dealer.setDealerName(rs.getString("dealer_name"));
		dealer.setAddress(rs.getString("address"));
		dealer.setCity(rs.getString("city"));
		dealer.setMobile(rs.getString("mobile"));
		dealer.setPhone(rs.getString("phone"));
		dealer.setStateName(rs.getString("state_name"));
		dealer.setZipcode(rs.getString("zipcode"));
		dealer.setCreateTimestamp(rs.getTimestamp("create_ts"));
		dealer.setUpdateTimestamp(rs.getTimestamp("update_ts"));
		return dealer;
	}

}
