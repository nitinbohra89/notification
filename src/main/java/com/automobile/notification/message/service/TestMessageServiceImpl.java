package com.automobile.notification.message.service;

import java.security.ProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.message.dao.MessageTokensDAO;
import com.automobile.notification.message.domain.MessageTokenEntity;
import com.automobile.notification.message.exception.MessageTokensException;
import com.automobile.notification.message.model.MessageResponse;
import com.automobile.notification.message.model.TestMessage;
import com.automobile.notification.provider.dao.ProviderDAO;
import com.automobile.notification.provider.domain.ProviderAttributeEntity;
import com.automobile.notification.provider.domain.ProviderEntity;
import com.automobile.notification.utility.SMSClientUtility;

@Service("testMessageService")
public class TestMessageServiceImpl implements TestMessageService {
	final static Logger logger = Logger.getLogger(TestMessageServiceImpl.class);
	private static final String TOKEN_IDENTIFIER = "@";
	private static final String ATTRIBUTE_TYPE1="BODY";
	private static final String ATTRIBUTE_TYPE2="HEADER";
	private static final String ATTRIBUTE_MAPPING_MESSAGE="MESSAGE";
	private static final String ATTRIBUTE_MAPPING_TO="TO";


	@Autowired
	MessageTokensDAO messageTokensDAO;

	@Autowired
	ProviderDAO providerDAO;
	public MessageResponse sendTestMessage(TestMessage testMessage) {
		logger.debug("sendTestMessage:: START");
		MessageResponse response = new MessageResponse();
		try {
			String message = parseMessage(testMessage.getMessage());
			logger.debug("Setting Message--"+message);
			testMessage.setMessage(message);
			logger.debug("Message has been set");
			sendMessageUsingDefaultProvider(testMessage);
		} catch (Exception e) {
			logger.debug("Error ---"+e.getMessage());
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage("Error in sending message.");
		}
		logger.debug("sendTestMessage:: END");
		return response;
	}

	private String parseMessage(String message) throws MessageTokensException {
		logger.debug("parseMessage:: START");
		try {
			if (message.contains(TOKEN_IDENTIFIER)) {
				List<MessageTokenEntity> messageTokens = messageTokensDAO.getAllTokens();
				Map<String, String> tokenMap = getTokenMap(messageTokens);
				int index = 0, tokenIndex = 0, spaceIndex = 0;
				logger.debug("parseMessage:: Starting replacing Tokens.");
				while (index < message.length()) {
					tokenIndex = message.indexOf(TOKEN_IDENTIFIER, index);
					if (tokenIndex == -1) {
						break;
					} else {
						spaceIndex = message.indexOf(TOKEN_IDENTIFIER, tokenIndex+1);
						String token = message.substring(tokenIndex + 1, spaceIndex);
						if (tokenMap.containsKey(token)) {
							message = message.replace(TOKEN_IDENTIFIER+token+TOKEN_IDENTIFIER, tokenMap.get(token));
						}
						index = spaceIndex;
					}
				}
			}
		} catch (MessageTokensException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MessageTokensException("Error in parsing Message.");
		}
		logger.debug("parseMessage:: END");

		return message;
	}

	private Map<String, String> getTokenMap(List<MessageTokenEntity> tokenList) {
		Map<String, String> tokenMap = new HashMap<>();
		for (MessageTokenEntity entity : tokenList) {
			tokenMap.put(entity.getTokenName(), entity.getDefaultValue());
		}
		return tokenMap;
	}
	
	private void sendMessageUsingDefaultProvider(TestMessage testMessage) throws MessageTokensException {
		logger.debug("sendMessageUsingDefaultProvider:: START");
	try{
			ProviderEntity provider=providerDAO.getDefaultProvider();
			List<ProviderAttributeEntity> providerAttributes=providerDAO.getProviderAttributes(provider.getProviderId());
			Map<String,String> bodyMap=getBodyJson(testMessage,providerAttributes);
			SMSClientUtility.sendPostMessage(provider.getProviderURL(), bodyMap);			
		}catch(ProviderException e ){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new MessageTokensException("Error in sending Message.");	
		}
	}
	
	private  Map<String,String> getBodyJson(TestMessage testMessage,List<ProviderAttributeEntity> providerAttributes ){
		Map<String,String> providerAttributeMap=new HashMap<>();
		try{
			logger.debug("getBodyJson:: START--"+providerAttributes.size());
		for(ProviderAttributeEntity entity : providerAttributes){
			logger.debug(entity.getAttributeName()+" "+entity.getAttributeMapping()+" "+entity.getAttributeType()+" "+entity.getAttributeValue());

			if(entity.getAttributeType().equals(ATTRIBUTE_TYPE1)){
				if(entity.getAttributeMapping()==null){
					providerAttributeMap.put(entity.getAttributeName(), entity.getAttributeValue());
				}else if(entity.getAttributeMapping().equals(ATTRIBUTE_MAPPING_MESSAGE)){
					providerAttributeMap.put(entity.getAttributeName(), testMessage.getMessage());
				}else if(entity.getAttributeMapping().equals(ATTRIBUTE_MAPPING_TO)){
					providerAttributeMap.put(entity.getAttributeName(), testMessage.getMobileNo());
				}else{
					providerAttributeMap.put(entity.getAttributeName(), entity.getAttributeValue());
				}
			}
		}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.debug("getBodyJson:: END "+providerAttributeMap);
		return providerAttributeMap;
	}
	
}
