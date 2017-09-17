package com.automobile.notification.vehicle.dao;

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

import com.automobile.notification.utility.DBUtility;
import com.automobile.notification.vehicle.domain.VehicleNotificationEntity;
import com.automobile.notification.vehicle.exception.VehicleException;
import com.automobile.notification.vehicle.exception.VehicleNotificationException;

@Repository("vehicleNotificationDAO")
@Lazy
@Scope("singleton")
public class VehicleNotificationDAOImpl implements VehicleNotificationDAO {
	private static final String CREATE_VNOTIFICATION_SQL = "INSERT INTO VEHICLE_NOTIFICATION "
			+ "(vehicle_chesis,store_id,ro_number,ro_close_date,next_service_due_date,customer_id,create_ts,created_by)"
			+ "VALUES(?,?,?,?,?,?,?,?)";
	private static final String UPDATE_VNOTIFICATION_SQL = "UPDATE VEHICLE_NOTIFICATION SET "
			+ "ro_number=?,ro_close_date=?,next_service_due_date=?,store_id=?,"
			+ "last_notified_date=?,notification_count=?,update_ts=?,updated_by=? WHERE vehicle_chesis=?";
	private static final String GET_VNOTIFICATION_SQL = "SELECT * FROM VEHICLE_NOTIFICATION";
	private static final String GET_VNOTIFICATION_BY_VEHICLE_ID_SQL = "SELECT * FROM VEHICLE_NOTIFICATION WHERE vehicle_chesis=?";
	private static final String GET_VNOTIFICATION_BY_STORE_ID_SQL = "SELECT * FROM VEHICLE_NOTIFICATION WHERE store_id=?";
	private static final String DELETE_VNOTIFICATION_SQL = "DELETE FROM VEHICLE_NOTIFICATION WHERE vehicle_id=?  AND store_id=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public VehicleNotificationEntity create(VehicleNotificationEntity vnEntity) throws VehicleNotificationException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(CREATE_VNOTIFICATION_SQL,
					new Object[] { vnEntity.getVehicleChesisNo(), vnEntity.getStoreId(), vnEntity.getServiceOrderId(),
							vnEntity.getServiceOrderCloseDate(), vnEntity.getNextServiceDueDate(),
							vnEntity.getCustomerId(), new Timestamp(new Date().getTime()), "system" });
			return vnEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleNotificationException("Error in Creating new Vehicle Notification.");
		}
	}

	@Override
	public VehicleNotificationEntity update(VehicleNotificationEntity oldEntity, VehicleNotificationEntity vnEntity)
			throws VehicleNotificationException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(UPDATE_VNOTIFICATION_SQL,
					new Object[] { vnEntity.getServiceOrderId(), vnEntity.getServiceOrderCloseDate(),
							vnEntity.getNextServiceDueDate(), vnEntity.getStoreId(), oldEntity.getLastNotifiedDate(),
							new Integer(0), new Timestamp(new Date().getTime()), "system" },
					vnEntity.getVehicleChesisNo(), vnEntity.getStoreId());
			return vnEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleNotificationException("Error in Creating new Vehicle Notification.");
		}
	}

	@Override
	public VehicleNotificationEntity getVehicleNotificationByVehicleChesis(String vehicleChesis)
			throws VehicleNotificationException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			VehicleNotificationEntity vnotification = (VehicleNotificationEntity) jdt.queryForObject(
					GET_VNOTIFICATION_BY_VEHICLE_ID_SQL, new Object[] { vehicleChesis },
					new VehicleNotificationMapper());
			return vnotification;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleNotificationException("Error in fetching Vehicle Notification Details.");
		}
	}

	@Override
	public List<VehicleNotificationEntity> getVehicleNotification() {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		List<VehicleNotificationEntity> vnotifications = jdt.query(GET_VNOTIFICATION_SQL,
				new BeanPropertyRowMapper(VehicleNotificationEntity.class));
		return vnotifications;
	}

	@Override
	public Long delete(long vnId) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		int row = jdt.update(DELETE_VNOTIFICATION_SQL, new Object[] { vnId });
		if (row > 0)
			return vnId;
		else
			return new Long(0);
	}

	@Override
	public VehicleNotificationEntity getVehicleNotificationByStoreId(long storeId) {
		JdbcTemplate jdt = dbUtility.geJdbcTemplate();
		VehicleNotificationEntity vnotification = (VehicleNotificationEntity) jdt.queryForObject(
				GET_VNOTIFICATION_BY_STORE_ID_SQL, new Object[] { storeId }, new VehicleNotificationMapper());
		return vnotification;
	}

}

class VehicleNotificationMapper implements RowMapper<VehicleNotificationEntity> {

	@Override
	public VehicleNotificationEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		VehicleNotificationEntity vnotification = new VehicleNotificationEntity();
		vnotification.setVehicleChesisNo(rs.getString("vehicle_chesis"));
		vnotification.setStoreId(rs.getLong("store_id"));
		vnotification.setServiceOrderId(rs.getLong("ro_number"));
		vnotification.setCustomerId(rs.getLong("customer_id"));
		vnotification.setServiceOrderCloseDate(rs.getDate("ro_close_date"));
		vnotification.setLastNotifiedDate(rs.getDate("last_notified_date"));
		vnotification.setNextServiceDueDate(rs.getDate("next_service_due_date"));
		vnotification.setNotificationCount(rs.getInt("notification_count"));
		return vnotification;
	}
}
