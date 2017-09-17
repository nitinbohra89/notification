package com.automobile.notification.message.dao;

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

import com.automobile.notification.message.domain.MessageEntity;
import com.automobile.notification.message.exception.MessageException;
import com.automobile.notification.utility.DBUtility;

@Repository("messageDAO")
@Lazy
@Scope("singleton")
public class MessageDAOImpl implements MessageDAO {

	private static final String CREATE_DEFAULT_MESSAGE_SQL = "INSERT INTO MESSAGE(message_type,message,"
			+ "create_ts,created_by) VALUES(?,?,?,?)";
	private static final String UPDATE_DEFAULT_MESSAGE_SQL = "UPDATE MESSAGE SET message=?,"
			+ "update_ts=?,updated_by=? WHERE message_type=?";
	private static final String GET_DEFAULT_MESSAGE_SQL = "SELECT * FROM MESSAGE WHERE message_type=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public MessageEntity getDefaultMessage() throws MessageException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			MessageEntity token = (MessageEntity) jdt.queryForObject(GET_DEFAULT_MESSAGE_SQL,
					new Object[] { "DEFAULT" }, new MessageMapper());

			return token;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageException("Error in fetching Default Message.");
		}
	}

	@Override
	public MessageEntity updateDefaultMessage(MessageEntity messageEntity) throws MessageException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			int count =jdt.update(UPDATE_DEFAULT_MESSAGE_SQL,
					new Object[] { messageEntity.getMessage(), new Timestamp(new Date().getTime()),
							messageEntity.getCreatedBy(), messageEntity.getMessageType() });
			System.out.println("count---"+count);

			return messageEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageException("Error in updating Default Message.");
		}
	}

	@Override
	public MessageEntity insertDefaultMessage(MessageEntity messageEntity) throws MessageException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdt.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pst = con.prepareStatement(CREATE_DEFAULT_MESSAGE_SQL,
							new String[] { "message_id" });
					pst.setString(1, messageEntity.getMessageType());
					pst.setString(2, messageEntity.getMessage());
					pst.setTimestamp(3, new Timestamp(new Date().getTime()));
					pst.setString(4, messageEntity.getCreatedBy());
					return pst;
				}
			}, keyHolder);
			System.out.println("message_id---"+keyHolder.getKey().longValue());
			messageEntity.setMessageId(keyHolder.getKey().longValue());
			return messageEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageException("Error in creating Default Message.");
		}
	}

}

class MessageMapper implements RowMapper<MessageEntity> {

	@Override
	public MessageEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setMessageId(rs.getLong("message_id"));
		messageEntity.setMessage(rs.getString("message"));
		messageEntity.setMessageType(rs.getString("message_type"));
		return messageEntity;
	}
}
