package com.automobile.notification.message.dao;

import java.util.List;

import com.automobile.notification.message.domain.MessageTokenEntity;
import com.automobile.notification.message.exception.MessageTokensException;

public interface MessageTokensDAO {
	public List<MessageTokenEntity> getAllTokens() throws MessageTokensException;

	public MessageTokenEntity createToken(MessageTokenEntity mte) throws MessageTokensException;

	public MessageTokenEntity getTokenByName(String tokenName) throws MessageTokensException;

	public MessageTokenEntity updateTokenValue(MessageTokenEntity mte) throws MessageTokensException;

	public MessageTokenEntity getTokenById(Integer tokenId) throws MessageTokensException;

	public void deleteToken(Integer tokenId) throws MessageTokensException;
}
