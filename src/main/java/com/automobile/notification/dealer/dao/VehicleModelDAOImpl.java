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

import com.automobile.notification.dealer.domain.VehicleModelEntity;
import com.automobile.notification.dealer.exception.VehicleModelException;
import com.automobile.notification.utility.DBUtility;

@Repository("vehicleModelDAO")
@Lazy
@Scope("singleton")
public class VehicleModelDAOImpl implements VehicleModelDAO {
	private static final String CREATE_MODEL_SQL = "INSERT INTO MODEL "
			+ "(model_id,model_name,dealer_id,create_ts,created_by)" + "VALUES(?,?,?,?,?)";
	private static final String GET_MODEL_BY_ID_SQL = "SELECT * FROM MODEL WHERE model_id=?";

	@Autowired(required = true)
	private DBUtility dbUtility;

	@Override
	public VehicleModelEntity create(VehicleModelEntity modelEntity) throws VehicleModelException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(CREATE_MODEL_SQL, new Object[] { modelEntity.getModelId(), modelEntity.getModelName(),
					modelEntity.getDealerId(), new Timestamp(new Date().getTime()), "system" });
			return modelEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleModelException("Error in creating Vehicle Model.");
		}
	}

	@Override
	public VehicleModelEntity getModelById(String modelId) throws VehicleModelException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			VehicleModelEntity model = (VehicleModelEntity) jdt.queryForObject(GET_MODEL_BY_ID_SQL, new Object[] { modelId },
					new ModelMapper());
			return model;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleModelException("Error in fetching Vehicle Model.");
		}
	}
}

class ModelMapper implements RowMapper<VehicleModelEntity> {

	@Override
	public VehicleModelEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		VehicleModelEntity model = new VehicleModelEntity();
		model.setModelId(rs.getString("model_id"));
		model.setModelName(rs.getString("model_name"));
		model.setDealerId(rs.getString("dealer_id"));
		return model;
	}

}