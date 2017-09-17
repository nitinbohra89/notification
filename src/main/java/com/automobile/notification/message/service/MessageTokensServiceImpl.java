package com.automobile.notification.message.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.message.dao.MessageTokensDAO;
import com.automobile.notification.message.domain.MessageTokenEntity;
import com.automobile.notification.message.exception.MessageTokensException;
import com.automobile.notification.message.model.MessageToken;
import com.automobile.notification.message.model.MessageTokenRequest;
import com.automobile.notification.message.model.MessageTokensResponse;
import com.automobile.notification.message.validator.MessageTokensValidator;

@Service("messageTokenService")
public class MessageTokensServiceImpl implements MessageTokensService {

	@Autowired
	MessageTokensDAO messageTokensDAO;

	public MessageTokensResponse getAllTokens(String username) {
		MessageTokensResponse response = new MessageTokensResponse();
		List<MessageToken> tokens = new ArrayList<>();
		try {
			List<MessageTokenEntity> messageTokens = messageTokensDAO.getAllTokens();
			for (MessageTokenEntity entity : messageTokens) {
				tokens.add(convertMessageTokenEntityToMessageToken(entity));
			}
			response.setMessageTokens(tokens);
			response.setStatus("SUCCESS");
			response.setUsername(username);
			return response;

		} catch (Exception e) {
			response.setErrorCode("100");
			response.setErrorCode("Error in Fetching Message Tokens.");
		}
		return response;

	}

	private MessageToken convertMessageTokenEntityToMessageToken(MessageTokenEntity entity)
			throws MessageTokensException {
		try {
			MessageToken token = new MessageToken();

			token.setTokenId(entity.getTokenId());
			token.setTokenName(entity.getTokenName());
			token.setTokenType(entity.getTokenType());
			token.setDefaultValue(entity.getDefaultValue());
			return token;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageTokensException("Error in converting Entity to Token.");
		}
	}

	@Override
	public MessageTokensResponse createOrUpdateToken(String username, MessageTokenRequest tokenRequest) {
		MessageTokensResponse mtr = new MessageTokensResponse();
		try {
			MessageTokensValidator.validateTokenAttributes(tokenRequest);
			MessageTokenEntity mte = convertTokenRequestToTokenEntity(tokenRequest, username);

			if (tokenRequest.getTokenId() == null) {
				System.out.println("tokenRequest.getTokenId()==null---" + tokenRequest.getTokenId());

				MessageTokenEntity existingToken = messageTokensDAO.getTokenByName(tokenRequest.getTokenName());
				// System.out.println("existingToken---"+existingToken.getTokenName());
				if (existingToken != null) {
					mtr.setStatus("ERROR");
					mtr.setErrorMessage("Token with this Name already exist. Please use some other name.");
					mtr.setErrorCode("101");
				} else {
					mte = messageTokensDAO.createToken(mte);
					mtr.setStatus("SUCCESS");
				}
			} else {
				MessageTokenEntity existingToken = messageTokensDAO.getTokenById(tokenRequest.getTokenId());
				if (existingToken == null) {
					mtr.setStatus("ERROR");
					mtr.setErrorMessage("Token does not exist.");
					mtr.setErrorCode("101");
				} else {
					if (existingToken.getTokenType().equals("DYNAMIC")) {
						mtr.setStatus("ERROR");
						mtr.setErrorMessage("This is Dynamic Token. Value can not be updated.");
						mtr.setErrorCode("101");
					} else {
						messageTokensDAO.updateTokenValue(mte);
						mtr.setStatus("SUCCESS");
					}
				}

			}

		} catch (MessageTokensException e) {
			mtr.setStatus("ERROR");
			mtr.setErrorMessage(e.getMessage());
			mtr.setErrorCode("101");
		} catch (Exception e) {
			mtr.setStatus("ERROR");
			mtr.setErrorMessage("Error in Creating Message Token.");
			mtr.setErrorCode("101");
		}
		return mtr;
	}

	private MessageTokenEntity convertTokenRequestToTokenEntity(MessageTokenRequest tokenRequest, String username) {
		MessageTokenEntity mte = new MessageTokenEntity();
		if (tokenRequest.getTokenId() != null)
			mte.setTokenId(new Long(tokenRequest.getTokenId()));
		mte.setTokenName(tokenRequest.getTokenName());
		mte.setTokenType("STATIC");
		mte.setDefaultValue(tokenRequest.getTokenValue());
		mte.setCreatedBy(username);
		return mte;
	}

	@Override
	public MessageTokensResponse deleteToken(Integer tokenId) {
		MessageTokensResponse mtr = new MessageTokensResponse();
		try {
			MessageTokenEntity mte = messageTokensDAO.getTokenById(tokenId);
			if (mte == null) {
				mtr.setStatus("ERROR");
				mtr.setErrorMessage("Token does not exist.");
				mtr.setErrorCode("101");
			} else {
				if (mte.getTokenType().equals("DYNAMIC")) {
					mtr.setStatus("ERROR");
					mtr.setErrorMessage("Dynamic Token can not be deleted.");
					mtr.setErrorCode("101");
				} else {
					messageTokensDAO.deleteToken(tokenId);
				}
				mtr.setStatus("SUCCESS");
			}

		} catch (MessageTokensException e) {
			mtr.setStatus("ERROR");
			mtr.setErrorMessage(e.getMessage());
			mtr.setErrorCode("101");
		} catch (Exception e) {
			mtr.setStatus("ERROR");
			mtr.setErrorMessage("Error in Creating Message Token.");
			mtr.setErrorCode("101");
		}
		return mtr;
	}

}
