package com.automobile.notification.message.dao;

import com.automobile.notification.message.domain.MessageEntity;
import com.automobile.notification.message.exception.MessageException;

public interface MessageDAO {
	public MessageEntity getDefaultMessage() throws MessageException;

	public MessageEntity updateDefaultMessage(MessageEntity messageEntity) throws MessageException;
	
	public MessageEntity insertDefaultMessage(MessageEntity messageEntity) throws MessageException;
}
