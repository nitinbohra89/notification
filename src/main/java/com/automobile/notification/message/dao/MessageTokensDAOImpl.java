package com.automobile.notification.message.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.automobile.notification.message.domain.MessageTokenEntity;
import com.automobile.notification.message.exception.MessageTokensException;
import com.automobile.notification.message.service.TestMessageServiceImpl;
import com.automobile.notification.utility.DBUtility;

@Repository("messageTokensDAO")
@Lazy
@Scope("singleton")
public class MessageTokensDAOImpl implements MessageTokensDAO {

	private static final String CREATE_MESSAGE_TOKENS_SQL = "INSERT INTO MESSAGE_TOKENS(token_name,token_type,default_value"
			+ ",create_ts,created_by) VALUES(?,?,?,?,?)";
	private static final String GET_ALL_MESSAGE_TOKENS_SQL = "SELECT * FROM MESSAGE_TOKENS ORDER BY token_name";
	private static final String GET_MESSAGE_TOKENS_BY_NAME_SQL = "SELECT * FROM MESSAGE_TOKENS WHERE UPPER(token_name)=UPPER(?)";
	private static final String GET_MESSAGE_TOKENS_BY_ID_SQL = "SELECT * FROM MESSAGE_TOKENS WHERE token_id=?";
	private static final String UPDATE_MESSAGE_TOKEN_VALUE_SQL = "UPDATE MESSAGE_TOKENS SET token_name=?,"
			+ "default_value=?,update_ts=?,updated_by=? WHERE token_id=?";
	private static final String DELETE_TOKEN_BY_ID_SQL = "DELETE FROM MESSAGE_TOKENS WHERE token_id=?";

	final static Logger logger = Logger.getLogger(MessageTokensDAOImpl.class);

	@Autowired(required = true)
	private DBUtility dbUtility;

	public List<MessageTokenEntity> getAllTokens() throws MessageTokensException {
		logger.debug("getAllTokens ::START");
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			List<MessageTokenEntity> tokenEntities = jdt.query(GET_ALL_MESSAGE_TOKENS_SQL,
					new BeanPropertyRowMapper(MessageTokenEntity.class));
			logger.debug("getAllTokens ::END");
			return tokenEntities;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MessageTokensException("Error in fetching Message Tokens.");
		}
	}

	@Override
	public MessageTokenEntity createToken(MessageTokenEntity mte) throws MessageTokensException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdt.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pst = con.prepareStatement(CREATE_MESSAGE_TOKENS_SQL,
							new String[] { "token_id" });
					pst.setString(1, mte.getTokenName());
					pst.setString(2, mte.getTokenType());
					pst.setString(3, mte.getDefaultValue());
					pst.setTimestamp(4, new Timestamp(new Date().getTime()));
					pst.setString(5, mte.getCreatedBy());
					return pst;
				}
			}, keyHolder);
			mte.setTokenId(keyHolder.getKey().longValue());
			return mte;
		} catch (Exception e) {
			throw new MessageTokensException("Error in creating Message Token.");
		}
	}

	@Override
	public MessageTokenEntity getTokenByName(String tokenName) throws MessageTokensException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			MessageTokenEntity token = (MessageTokenEntity) jdt.queryForObject(GET_MESSAGE_TOKENS_BY_NAME_SQL,
					new Object[] { tokenName }, new MessageTokensMapper());
			return token;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageTokensException("Error in fetching Message Token.");
		}
	}

	@Override
	public MessageTokenEntity updateTokenValue(MessageTokenEntity mte) throws MessageTokensException {
		try {
			System.out.println("updateTokenValue" + mte.getDefaultValue() + "   " + mte.getTokenId());
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			int count = jdt.update(UPDATE_MESSAGE_TOKEN_VALUE_SQL, new Object[] { mte.getTokenName(),
					mte.getDefaultValue(), new Timestamp(new Date().getTime()), mte.getCreatedBy(), mte.getTokenId() });
			System.out.println("updateTokenValue count ---" + count);

			return mte;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageTokensException("Error in updating Message Token.");
		}
	}

	@Override
	public MessageTokenEntity getTokenById(Integer tokenId) throws MessageTokensException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			MessageTokenEntity token = (MessageTokenEntity) jdt.queryForObject(GET_MESSAGE_TOKENS_BY_ID_SQL,
					new Object[] { tokenId }, new MessageTokensMapper());

			return token;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageTokensException("Error in fetching Message Token.");
		}
	}

	@Override
	public void deleteToken(Integer tokenId) throws MessageTokensException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			int count = jdt.update(DELETE_TOKEN_BY_ID_SQL, new Object[] { tokenId });
			System.out.println("updateTokenValue count ---" + count);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageTokensException("Error in Deleting Message Token.");
		}
	}
}

class MessageTokensMapper implements RowMapper<MessageTokenEntity> {

	@Override
	public MessageTokenEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		MessageTokenEntity mtEntity = new MessageTokenEntity();
		mtEntity.setTokenId(rs.getLong("token_id"));
		mtEntity.setTokenName(rs.getString("token_name"));
		mtEntity.setTokenType(rs.getString("token_type"));
		mtEntity.setDefaultValue(rs.getString("default_value"));
		mtEntity.setTableName(rs.getString("table_name"));
		mtEntity.setColumnName(rs.getString("column_name"));
		mtEntity.setJoinCondition(rs.getString("join_condition"));
		return mtEntity;
	}
}
