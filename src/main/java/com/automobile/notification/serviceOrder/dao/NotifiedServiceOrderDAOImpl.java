package com.automobile.notification.serviceOrder.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.automobile.notification.serviceOrder.exception.NotifiedServiceOrderException;
import com.automobile.notification.serviceOrder.exception.ServiceOrderException;
import com.automobile.notification.serviceOrder.model.NotifiedServiceOrder;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;
import com.automobile.notification.utility.DBUtility;

@Repository("notifiedServiceOrderDAO")
@Lazy
@Scope("singleton")
public class NotifiedServiceOrderDAOImpl implements NotifiedServiceOrderDAO{

	
	private static final Map<Integer, String> conditionMap = new HashMap<>();
	private static final String GET_NOTIFIED_SERVICE_ORDER_SQL=" SELECT SSR.sms_id as notificationId,SSR.notification_count as notificationCount,SSR.not_interval_id as intervalId,"
			+" NI.days as intervalDays,SSR.message as message,	SSR.notification_date as notificationDate,	SSR.is_success as status,"
			+" SSR.error_message as errorMessage,	PC.provider_id as providerId,	PC.provider_name as providerName,"
			+" VE.vehicle_chesis as vehicleChesis,	VE.license_no as vehicleLicense,	DL.dealer_id as dealerId,"
			+" DL.dealer_name as dealerName,	ST.store_id as storeId,	ST.store_name as storeName,	CUST.customer_id as customerId,"
			+" CUST.customer_name as customerName,	CUST.address as customerAddress,	CUST.city as customerCity,"
			+" CUST.state_name as customerState,	CUST.zipcode as customerZip,	CUST.phone as customerPhone,	CUST.mobile as customerMobile,"
			+" CUST.email as customerEmail,	CCAT.category_name as category,	RO.ro_number as serviceOrderId,	RO.ro_type as serviceOrderType,"
			+" RO.ro_open_date as serviceOrderOpenDate,	RO.ro_close_date as serviceOrderCloseDate,	RO.receiver as receiver,"
			+" RO.operation_rerformed1 as operation1,	RO.operation_rerformed2 as operation2,	RO.mileage as mileage,"
			+" RO.next_service_due_date as nextServiceDueDate,	MD.model_id as modelId,	MD.model_name as modelName"
			+" FROM 	SMS_SENT_REPORT SSR,	VEHICLE VE,	REPAIR_ORDER RO,	CUSTOMER CUST,	STORE ST,	DEALER DL,	CUSTOMER_CATEGORY CCAT,"
			+" MODEL MD,	PROVIDER_CONFIGURATION PC,	NOTIFICATION_INTERVAL NI"
			+" WHERE 	VE.vehicle_chesis=SSR.vehicle_chesis AND		SSR.ro_number=RO.ro_number AND	SSR.store_id=ST.store_id AND	"
			+" SSR.customer_id=CUST.customer_id AND	CUST.cust_category_id=CCAT.cust_category_id AND		VE.dealer_id=DL.dealer_id AND"
			+" VE.model_id=MD.model_id AND	SSR.provider_id=PC.provider_id AND	SSR.not_interval_id=NI.not_interval_id";
	public NotifiedServiceOrderDAOImpl() {
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
		conditionMap.put(21, "SSR.notification_count");
		conditionMap.put(22, "SSR.is_success");
		conditionMap.put(23, "PC.provider_name");
	}
	
	@Autowired(required = true)
	private DBUtility dbUtility;
	
	@Override
	public List<NotifiedServiceOrder> getNotifiedOrders(ServiceOrderSearchRequest searchRequest)
			throws NotifiedServiceOrderException {
		try {
			System.out.println("getServiceOrders DAO");

			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			String SQL_QUERY=GET_NOTIFIED_SERVICE_ORDER_SQL;
			if (searchRequest.getSearchAttributeIndex() != 0) {
				if (conditionMap.containsKey(searchRequest.getSearchAttributeIndex())) {
					SQL_QUERY = prepareGetServiceOrderString(searchRequest.getSearchAttributeIndex(),
							searchRequest.getAttributeValue());
					System.out.println(SQL_QUERY);
				} else {
					throw new ServiceOrderException("This Search Criteria Does not exist.");
				}
			}
			List<NotifiedServiceOrder> notifiedServiceOrders = jdt.query(SQL_QUERY,
					new BeanPropertyRowMapper(NotifiedServiceOrder.class));
			return notifiedServiceOrders;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			throw new NotifiedServiceOrderException("Error in fetching Notified Service Order Details.");
		}	
	}
	private String prepareGetServiceOrderString(int index, String value) {
		return GET_NOTIFIED_SERVICE_ORDER_SQL + " AND " + conditionMap.get(index) + " LIKE ('%" + value + "%')"+ " ORDER BY VE.create_ts desc";
	}

}
