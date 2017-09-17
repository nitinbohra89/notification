package com.automobile.notification.vehicle.dao;

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

import com.automobile.notification.utility.DBUtility;
import com.automobile.notification.vehicle.domain.VehicleEntity;
import com.automobile.notification.vehicle.exception.VehicleException;

@Repository("vehicleDAO")
@Lazy
@Scope("singleton")
public class VehicleDAOImpl implements VehicleDAO {

	private static final String CREATE_VEHICLE_SQL = "INSERT INTO VEHICLE "
			+ "(vehicle_chesis,license_no,customer_id,model_id,dealer_id,create_ts,created_by)"
			+ "VALUES(?,?,?,?,?,?,?)";
	private static final String GET_VEHICLE_BY_CHESIS_SQL = "SELECT * FROM VEHICLE WHERE vehicle_chesis=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public VehicleEntity create(VehicleEntity vehicleEntity) throws VehicleException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(CREATE_VEHICLE_SQL,
					new Object[] { vehicleEntity.getChassis(), vehicleEntity.getLicenseNo(),
							vehicleEntity.getCustomerId(), vehicleEntity.getModelId(), vehicleEntity.getDealerId(),
							new Timestamp(new Date().getTime()), "system" });
			return vehicleEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleException("Error in creating Vehicle");
		}

	}

	@Override
	public VehicleEntity getVehicleByChesis(String vehicleChesis) throws VehicleException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			VehicleEntity vehicle = (VehicleEntity) jdt.queryForObject(GET_VEHICLE_BY_CHESIS_SQL,
					new Object[] { vehicleChesis }, new VehicleMapper());
			return vehicle;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleException("Error in fetching Vehicle Details.");
		}
	}

}

class VehicleMapper implements RowMapper<VehicleEntity> {

	@Override
	public VehicleEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		VehicleEntity vehicle = new VehicleEntity();
		vehicle.setChassis(rs.getString("vehicle_chesis"));
		vehicle.setLicenseNo(rs.getString("license_no"));
		vehicle.setCustomerId(rs.getString("customer_id"));
		vehicle.setDealerId(rs.getString("dealer_id"));
		vehicle.setModelId(rs.getString("model_id"));
		return vehicle;
	}
}
