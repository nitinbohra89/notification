package com.automobile.notification.provider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.automobile.notification.utility.DBUtility;

@Repository("providerDAO")
@Lazy
@Scope("singleton")
public class ProviderDAOImpl implements ProviderDAO{
/*	private static final String CREATE_PROVIDER_SQL = "INSERT INTO SMS_PROVIDER "
			+ "(provider_name,provider_url)" + "VALUES(?,?)";
	private static final String UPDATE_PROVIDER_SQL = "UPDATE SMS_PROVIDER SET "
			+ "provider_name=?,provider_url=?,update_ts=? WHERE provider_id=?";
	private static final String GET_PROVIDER_SQL = "SELECT * FROM SMS_PROVIDER";
	private static final String GET_PROVIDER_BY_ID_SQL = "SELECT * FROM SMS_PROVIDER WHERE provider_id=?";
	private static final String DELETE_PROVIDER_SQL = "DELETE FROM SMS_PROVIDER WHERE provider_id=?";

	@Autowired(required = true)
	private DBUtility dbUtility;
	
	@Override
	public ProviderEntity create(ProviderEntity providerEntity) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdt.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(CREATE_PROVIDER_SQL, new String[] { "provider_id" });
				pst.setString(1,providerEntity.getProviderName());
				pst.setString(2, providerEntity.getProviderUrl());
				return pst;
			}
		}, keyHolder);
		providerEntity.setProviderId(keyHolder.getKey().longValue());
		return providerEntity;
	}

	@Override
	public ProviderEntity update(ProviderEntity providerEntity) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int rowCount = jdt.update(UPDATE_PROVIDER_SQL,
				new Object[] {providerEntity.getProviderName(),
						providerEntity.getProviderUrl(),
						new Timestamp(new Date().getTime()),
						providerEntity.getProviderId()});

		if (rowCount == 0)
			return null;
		else
			return providerEntity;
	}

	@Override
	public ProviderEntity getProviderById(long providerId) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		ProviderEntity provider = (ProviderEntity) jdt.queryForObject(GET_PROVIDER_BY_ID_SQL, new Object[] { providerId },
				new ProviderMapper());
		return provider;
	}

	@Override
	public List<ProviderEntity> getProvider() {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		List<ProviderEntity> providers = jdt.query(GET_PROVIDER_SQL, new BeanPropertyRowMapper(ProviderEntity.class));
		return providers;
	}

	@Override
	public Long delete(long providerId) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int row = jdt.update(DELETE_PROVIDER_SQL, new Object[] { providerId });
		if (row > 0)
			return providerId;
		else
			return null;
	}
*/
}
/*class ProviderMapper implements RowMapper<ProviderEntity> {

	@Override
	public ProviderEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		ProviderEntity provider = new ProviderEntity();
		provider.setProviderId(rs.getLong("provider_id"));
		provider.setProviderName(rs.getString("provider_name"));
		provider.setProviderUrl(rs.getString("provider_url"));
		provider.setCreateTimestamp(rs.getTimestamp("create_ts"));
		provider.setUpdateTimestamp(rs.getTimestamp("update_ts"));
		return provider;
	}
}
*/