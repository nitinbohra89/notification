package com.automobile.notification.provider.dao;

import java.security.ProviderException;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.automobile.notification.provider.domain.DeliveryReceiptEntity;
import com.automobile.notification.provider.domain.ProviderAttributeEntity;
import com.automobile.notification.provider.domain.ProviderEntity;
import com.automobile.notification.provider.exception.DeliveryReceiptException;
import com.automobile.notification.provider.service.ProviderServiceImpl;
import com.automobile.notification.utility.DBUtility;

@Repository("providerDAO")
@Lazy
@Scope("singleton")
public class ProviderDAOImpl implements ProviderDAO {
	final static Logger logger = Logger.getLogger(ProviderServiceImpl.class);

	@Autowired(required = true)
	private DBUtility dbUtility;

	private static final String UPDATE_DELIVERY_RECEIPT_SQL = "UPDATE SMS_SENT_REPORT SET is_success=?,error_message=?,update_ts=?updated_by=? "
			+ "WHERE provider_message_id=?";
	private static final String GET_DEFAULT_PROVIDER_SQL = "SELECT * FROM PROVIDER_CONFIGURATION WHERE provider_type='DEFAULT'";
	private static final String GET_PROVIDER_ATTRIBUTES_BY_ID_SQL = "SELECT * FROM PROVIDER_ATTRIBUTES WHERE provider_id=?";

	@Override
	public void updateDeliveryReceipt(DeliveryReceiptEntity deliveryReceipt) throws DeliveryReceiptException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			jdt.update(UPDATE_DELIVERY_RECEIPT_SQL,
					new Object[] { deliveryReceipt.getStatus(), deliveryReceipt.getErrorMessage(),
							new Timestamp(new Date().getTime()), "system", deliveryReceipt.getMessageId() });
		} catch (Exception e) {
			logger.error("updateDeliveryReceipt--" + e.getMessage());
			throw new DeliveryReceiptException("Error in updating Delivery Receipt.");
		}

	}

	public ProviderEntity getDefaultProvider() throws ProviderException {
		try {
			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			ProviderEntity provider = (ProviderEntity) jdt.queryForObject(GET_DEFAULT_PROVIDER_SQL,
					new ProviderMapper());
			return provider;
		} catch (EmptyResultDataAccessException e) {
			throw new ProviderException("Default Provider is not available.");
		} catch (Exception e) {
			logger.error("getDefaultProvider--" + e.getMessage());
			throw new ProviderException("Error in fetching Default Provider.");
		}
	}

	public List<ProviderAttributeEntity> getProviderAttributes(int providerId) throws ProviderException {
		try {
			logger.debug("getProviderAttributes:: START");

			JdbcTemplate jdt = dbUtility.geJdbcTemplate();
			List<ProviderAttributeEntity> providerAttributes = jdt.query(GET_PROVIDER_ATTRIBUTES_BY_ID_SQL,new Object[]{providerId},
					new ProviderAttributeMapper());
			logger.debug("getProviderAttributes:: END"+ providerAttributes);
		return providerAttributes;
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
			throw new ProviderException("Provider Attributes are not available.");
		} catch (Exception e) {
			logger.error("getDefaultProvider--" + e.getMessage());
			throw new ProviderException("Error in fetching Provider Attributes.");
		}

	}
}

class ProviderMapper implements RowMapper<ProviderEntity> {

	@Override
	public ProviderEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		ProviderEntity provider = new ProviderEntity();
		provider.setProviderId(rs.getInt("provider_id"));
		provider.setProviderName(rs.getString("provider_name"));
		provider.setProviderType(rs.getString("provider_type"));
		provider.setProviderURL(rs.getString("provider_url"));
		provider.setProviderKey(rs.getString("provider_key"));
		provider.setProviderSecret(rs.getString("provider_secret"));
		provider.setProviderUsername(rs.getString("provider_username"));
		provider.setPasssword(rs.getString("provider_password"));
		return provider;
	}
}

class ProviderAttributeMapper implements RowMapper<ProviderAttributeEntity> {

	@Override
	public ProviderAttributeEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		ProviderAttributeEntity providerAttributes = new ProviderAttributeEntity();
		providerAttributes.setProviderId(rs.getInt("provider_id"));
		providerAttributes.setAttributeName(rs.getString("attribute_name"));
		providerAttributes.setAttributeMapping(rs.getString("attribute_mapping"));
		providerAttributes.setAttributeValue(rs.getString("attribute_value"));
		providerAttributes.setAttributeType(rs.getString("attribute_type"));
		return providerAttributes;
	}
}