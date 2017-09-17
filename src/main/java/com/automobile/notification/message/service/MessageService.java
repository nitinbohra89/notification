package com.automobile.notification.message.service;

import com.automobile.notification.message.model.Message;
import com.automobile.notification.message.model.MessageResponse;

public interface MessageService {
	public MessageResponse getMessage();

	public MessageResponse updateMessage(Message message,String username);
}
