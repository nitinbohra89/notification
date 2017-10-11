package com.automobile.notification.notificationInterval.dao;

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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.automobile.notification.notificationInterval.domain.NotificationIntervalEntity;
import com.automobile.notification.notificationInterval.exception.NotificationIntervalException;
import com.automobile.notification.utility.DBUtility;

@Repository("notificationDAO")
@Lazy
@Scope("singleton")
public class NotificationIntervalDAOImpl implements NotificationIntervalDAO {
	private static final String CREATE_NOTIFICATION_SQL = "INSERT INTO NOTIFICATION_INTERVAL "
			+ "(days,create_ts,created_by)" + "VALUES(?,?,?)";
	private static final String UPDATE_NOTIFICATION_SQL = "UPDATE NOTIFICATION_INTERVAL SET "
			+ "days=?,update_ts=?,updated_by=? WHERE not_interval_id=?";
	private static final String GET_NOTIFICATION_SQL = "SELECT * FROM NOTIFICATION_INTERVAL WHERE is_deleted='N' ORDER BY days";
	private static final String GET_NOTIFICATION_BY_ID_SQL = "SELECT * FROM NOTIFICATION_INTERVAL "
			+ "WHERE not_interval_id=? AND is_deleted='N'";
	private static final String DELETE_NOTIFICATION_SQL = "UPDATE NOTIFICATION_INTERVAL SET "
			+ "is_deleted=?,update_ts=?,updated_by=? WHERE not_interval_id=?";
	private static final String GET_NOTIFICATION_BY_DAYS_SQL = "SELECT * FROM NOTIFICATION_INTERVAL WHERE days=? AND is_deleted='N'";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public NotificationIntervalEntity create(NotificationIntervalEntity notificationEntity)
			throws NotificationIntervalException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdt.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pst = con.prepareStatement(CREATE_NOTIFICATION_SQL,
							new String[] { "not_interval_id" });
					pst.setInt(1, notificationEntity.getDays());
					pst.setTimestamp(2, new Timestamp(new Date().getTime()));
					pst.setString(3, notificationEntity.getCreatedBy());
					return pst;
				}
			}, keyHolder);
			notificationEntity.setIntervalId(keyHolder.getKey().intValue());
			return notificationEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationIntervalException("Error in creating Notification Interval.");
		}

	}

	@Override
	public NotificationIntervalEntity update(NotificationIntervalEntity notificationEntity)
			throws NotificationIntervalException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(UPDATE_NOTIFICATION_SQL,
					new Object[] { notificationEntity.getDays(), new Timestamp(new Date().getTime()),
							notificationEntity.getCreatedBy(), notificationEntity.getIntervalId() });
			return notificationEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationIntervalException("Error in updating Notification Interval.");
		}

	}

	@Override
	public NotificationIntervalEntity getNotificationById(Integer intervalId) throws NotificationIntervalException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			NotificationIntervalEntity notification = (NotificationIntervalEntity) jdt.queryForObject(
					GET_NOTIFICATION_BY_ID_SQL, new Object[] { intervalId }, new NotificationIntervalMapper());
			return notification;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationIntervalException("Error in fetching Notification Intervals.");
		}
	}

	@Override
	public List<NotificationIntervalEntity> getNotifications() throws NotificationIntervalException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			List<NotificationIntervalEntity> notifications = jdt.query(GET_NOTIFICATION_SQL,
					new NotificationIntervalMapper());
			return notifications;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationIntervalException("Error in fetching Notification Intervals.");
		}
	}

	@Override
	public void delete(Integer intervalId, String username) throws NotificationIntervalException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(DELETE_NOTIFICATION_SQL,
					new Object[] { "Y", new Timestamp(new Date().getTime()), username, intervalId });
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationIntervalException("Error in deleting Notification Intervals.");
		}
	}

	@Override
	public NotificationIntervalEntity searchNotificationByDays(Integer days) throws NotificationIntervalException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			NotificationIntervalEntity notification = (NotificationIntervalEntity) jdt.queryForObject(
					GET_NOTIFICATION_BY_DAYS_SQL, new Object[] { days }, new NotificationIntervalMapper());
			return notification;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationIntervalException("Error in fetching Notification Intervals.");
		}
	}

}

class NotificationIntervalMapper implements RowMapper<NotificationIntervalEntity> {

	@Override
	public NotificationIntervalEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		NotificationIntervalEntity notification = new NotificationIntervalEntity();
		notification.setIntervalId(rs.getInt("not_interval_id"));
		notification.setDays(rs.getInt("days"));
		return notification;
	}
}
