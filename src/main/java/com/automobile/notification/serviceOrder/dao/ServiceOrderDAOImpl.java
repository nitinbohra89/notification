package com.automobile.notification.serviceOrder.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.automobile.notification.serviceOrder.domain.ServiceOrderDetailsEntity;
import com.automobile.notification.serviceOrder.domain.ServiceOrderEntity;
import com.automobile.notification.serviceOrder.exception.ServiceOrderException;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;
import com.automobile.notification.utility.DBUtility;

@Repository("serviceOrderDAO")
@Lazy
@Scope("singleton")
public class ServiceOrderDAOImpl implements ServiceOrderDAO {
	private static final String CREATE_REPAIR_ORDER_SQL = "INSERT INTO REPAIR_ORDER "
			+ "(ro_number,ro_type,ro_open_date,ro_close_date,next_service_due_date,mileage,receiver,"
			+ "operation_rerformed1,operation_rerformed2,vehicle_chesis,customer_id,store_id,create_ts,created_by)"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_REPAIR_ORDER_SQL = "SELECT * FROM REPAIR_ORDER";
	private static final String GET_REPAIR_ORDER_BY_ID_SQL = "SELECT * FROM REPAIR_ORDER WHERE ro_number=?";

	private static String GET_REPAIR_ORDER_CONDITIONAL = "SELECT DL.dealer_id as dealerId,DL.dealer_name as dealerName,"
			+ " ST.store_id as storeId,ST.store_name as storeName,ST.address as storeAddress,ST.city as storeCity,"
			+ " ST.zipcode as storeZip,ST.state_name as storeState,ST.phone as storePhone,ST.mobile as storeMobile,"
			+ " CUST.customer_id as customerId,CUST.customer_name as customerName,CUST.address as customerAddress,"
			+ " CUST.city as customerCity,CUST.state_name as customerState,CUST.zipcode as customerZip,"
			+ " CUST.phone as customerPhone,CUST.mobile as customerMobile,CUST.email as customerEmail,"
			+ " CCAT.category_name as category,RO.ro_number as serviceOrderId,RO.ro_type as serviceOrderType,"
			+ " RO.ro_open_date as serviceOrderOpenDate,RO.ro_close_date as serviceOrderCloseDate,RO.receiver as receiver,"
			+ " RO.operation_rerformed1 as operation1,RO.operation_rerformed2 as operation2,RO.mileage as mileage,"
			+ " RO.next_service_due_date as nextServiceDueDate,VE.vehicle_chesis as vehicleChesis,"
			+ " VE.license_no as vehicleLicense,MD.model_id as modelId,MD.model_name as modelName"
			+ " FROM VEHICLE VE,REPAIR_ORDER RO,CUSTOMER CUST,STORE ST,"
			+ " DEALER DL,CUSTOMER_CATEGORY CCAT,VEHICLE_NOTIFICATION VN,MODEL MD"
			+ " WHERE VE.vehicle_chesis=VN.vehicle_chesis AND	VN.ro_number=RO.ro_number AND"
			+ " VN.store_id=ST.store_id AND	VN.customer_id=CUST.customer_id AND"
			+ " CUST.cust_category_id=CCAT.cust_category_id AND	VE.dealer_id=DL.dealer_id AND"
			+ " VE.model_id=MD.model_id";
	private static final Map<Integer, String> conditionMap = new HashMap<>();
	@Autowired(required = true)
	private DBUtility dbUtility;

	public ServiceOrderDAOImpl() {
		conditionMap.put(1, "DL.dealer_id");
		conditionMap.put(2, "DL.dealer_name");
		conditionMap.put(3, "ST.store_id");
		conditionMap.put(4, "ST.store_name");
		conditionMap.put(5, "CUST.customer_id");
		conditionMap.put(6, "CUST.customer_name");
		conditionMap.put(7, "CUST.address");
		conditionMap.put(8, "CUST.city");
		conditionMap.put(9, "CUST.state_name");
		conditionMap.put(10, "CUST.zipcode");
		conditionMap.put(11, "CUST.phone");
		conditionMap.put(12, "CUST.mobile");
		conditionMap.put(13, "CUST.email");
		conditionMap.put(14, "RO.ro_number");
		conditionMap.put(15, "RO.ro_type");
		conditionMap.put(16, "RO.receiver");
		conditionMap.put(17, "VE.vehicle_chesis");
		conditionMap.put(18, "VE.license_no");
		conditionMap.put(19, "MD.model_id");
		conditionMap.put(20, "MD.model_name");
	}

	@Override
	public ServiceOrderEntity create(ServiceOrderEntity serviceOrderEntity) throws ServiceOrderException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(CREATE_REPAIR_ORDER_SQL,
					new Object[] { serviceOrderEntity.getServiceOrderId(), serviceOrderEntity.getServiceOrderType(),
							serviceOrderEntity.getServiceOrderOpenDate(), serviceOrderEntity.getServiceOrderCloseDate(),
							serviceOrderEntity.getNextServiceDueDate(), serviceOrderEntity.getMileage(),
							serviceOrderEntity.getReceiver(), serviceOrderEntity.getOperationPerformed1(),
							serviceOrderEntity.getOperationPerformed2(), serviceOrderEntity.getVehicleChesis(),
							serviceOrderEntity.getCustomerId(), serviceOrderEntity.getStoreId(),
							new Timestamp(new Date().getTime()), "system" });
			return serviceOrderEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceOrderException("Error in creating Service Order.");
		}
	}

	@Override
	public ServiceOrderEntity getRepairOrderById(long roId) throws ServiceOrderException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			ServiceOrderEntity repairOrder = (ServiceOrderEntity) jdt.queryForObject(GET_REPAIR_ORDER_BY_ID_SQL,
					new Object[] { roId }, new RepairOrderMapper());
			return repairOrder;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceOrderException("Error in fetching Service Order.");
		}
	}

	@Override
	public List<ServiceOrderDetailsEntity> getServiceOrders(ServiceOrderSearchRequest searchRequest)
			throws ServiceOrderException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			String SQL_QUERY=GET_REPAIR_ORDER_CONDITIONAL;
			if (searchRequest.getSearchAttributeIndex() != 0) {
				if (conditionMap.containsKey(searchRequest.getSearchAttributeIndex())) {
					SQL_QUERY = prepareGetServiceOrderString(searchRequest.getSearchAttributeIndex(),
							searchRequest.getAttributeValue());
					System.out.println(SQL_QUERY);
				} else {
					throw new ServiceOrderException("This Search Criteria Does not exist.");
				}
			}
			List<ServiceOrderDetailsEntity> repairOrders = jdt.query(SQL_QUERY,
					new BeanPropertyRowMapper(ServiceOrderDetailsEntity.class));
			return repairOrders;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceOrderException("Error in fetching Service Order Details.");
		}
	}

	private String prepareGetServiceOrderString(int index, String value) {
		return GET_REPAIR_ORDER_CONDITIONAL + " AND " + conditionMap.get(index) + " LIKE ('%" + value + "%')"+ " ORDER BY VE.create_ts desc";
	}

}

class RepairOrderMapper implements RowMapper<ServiceOrderEntity> {

	@Override
	public ServiceOrderEntity mapRow(ResultSet rs, int rownum) throws SQLException {

		ServiceOrderEntity serviceOrderEntity = new ServiceOrderEntity();
		serviceOrderEntity.setServiceOrderId(rs.getLong("ro_number"));
		serviceOrderEntity.setServiceOrderType(rs.getString("ro_type"));
		serviceOrderEntity.setServiceOrderOpenDate(rs.getDate("ro_open_date"));
		serviceOrderEntity.setServiceOrderCloseDate(rs.getDate("ro_close_date"));
		serviceOrderEntity.setMileage(rs.getInt("mileage"));
		serviceOrderEntity.setNextServiceDueDate(rs.getDate("next_service_due_date"));
		serviceOrderEntity.setReceiver(rs.getString("receiver"));
		serviceOrderEntity.setOperationPerformed1(rs.getString("operation_rerformed1"));
		serviceOrderEntity.setOperationPerformed2(rs.getString("operation_rerformed2"));
		serviceOrderEntity.setVehicleChesis(rs.getString("vehicle_chesis"));
		serviceOrderEntity.setCustomerId(rs.getLong("customer_id"));
		serviceOrderEntity.setStoreId(rs.getLong("store_id"));
		return serviceOrderEntity;
	}

}

class RepairOrderDetailsMapper implements RowMapper<ServiceOrderDetailsEntity> {

	@Override
	public ServiceOrderDetailsEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		ServiceOrderDetailsEntity serviceOrderEntity = new ServiceOrderDetailsEntity();
		serviceOrderEntity.setDealerId(rs.getString("dealerId"));
		serviceOrderEntity.setDealerName(rs.getString("dealerName"));
		serviceOrderEntity.setStoreId(rs.getString("storeId"));
		serviceOrderEntity.setStoreName(rs.getString("storeName"));
		serviceOrderEntity.setStoreAddress(rs.getString("storeAddress"));
		serviceOrderEntity.setStoreCity(rs.getString("storeCity"));
		serviceOrderEntity.setStoreZipCode(rs.getString("storeZip"));
		serviceOrderEntity.setStoreState(rs.getString("storeState"));
	
	//	serviceOrderEntity.setStorePhoneNo(rs.getString("storePhone"));
	//	serviceOrderEntity.setStoreMobileNo(rs.getString("storeMobile"));
		serviceOrderEntity.setCustomerId(rs.getString("customerId"));
		serviceOrderEntity.setCustomerName(rs.getString("customerName"));
		serviceOrderEntity.setCustomerAddress(rs.getString("customerAddress"));
		serviceOrderEntity.setCustomerCity(rs.getString("customerCity"));
		serviceOrderEntity.setCustomerState(rs.getString("customerState"));
		serviceOrderEntity.setCustomerZipCode(rs.getString("customerZip"));
		serviceOrderEntity.setCustomerPhoneNo(rs.getString("customerPhone"));
		serviceOrderEntity.setCustomerMobileNo(rs.getString("customerMobile"));
		serviceOrderEntity.setCustomerEmail(rs.getString("customerEmail"));
	//	serviceOrderEntity.setCustomerCategory(rs.getString("category"));
		serviceOrderEntity.setServiceOrderId(rs.getString("serviceOrderId"));
		serviceOrderEntity.setServiceOrderType(rs.getString("serviceOrderType"));
		serviceOrderEntity.setServiceOrderOpenDate(rs.getString("serviceOrderOpenDate"));
		serviceOrderEntity.setServiceOrderCloseDate(rs.getString("serviceOrderCloseDate"));
	//	serviceOrderEntity.setReceiverName(rs.getString("receiver"));
		serviceOrderEntity.setOperationPerformed1(rs.getString("operation1"));
		serviceOrderEntity.setOperationPerformed2(rs.getString("operation2"));
		serviceOrderEntity.setMileage(rs.getString("mileage"));
		serviceOrderEntity.setNextServiceDueDate(rs.getString("nextServiceDueDate"));
		serviceOrderEntity.setVehicleChesisNo(rs.getString("vehicleChesis"));
		serviceOrderEntity.setVehicleLicense(rs.getString("vehicleLicense"));
		serviceOrderEntity.setVehicleModelId(rs.getString("modelId"));
		serviceOrderEntity.setVehicleModelName(rs.getString("modelName"));
		return serviceOrderEntity;
	}

}