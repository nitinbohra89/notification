package com.automobile.notification.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.message.dao.MessageDAO;
import com.automobile.notification.message.domain.MessageEntity;
import com.automobile.notification.message.exception.MessageException;
import com.automobile.notification.message.model.Message;
import com.automobile.notification.message.model.MessageResponse;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDAO messageDAO;

	@Override
	public MessageResponse getMessage() {
		MessageResponse mr = new MessageResponse();
		try {
			MessageEntity message = messageDAO.getDefaultMessage();
			if (message != null)
				mr.setNotificationMessage(message.getMessage());
			mr.setStatus("SUCCESS");
		} catch (Exception e) {
			mr.setErrorCode("101");
			mr.setErrorMessage("Error in fetching Message");
			mr.setStatus("ERROR");
		}
		return mr;
	}

	@Override
	public MessageResponse updateMessage(Message message, String username) {
		MessageResponse mr = new MessageResponse();
		try {
			System.out.println("updateMessage");
			MessageEntity messageEntity = convertMessageToMessageEntity(message, username);
			System.out.println("convertMessageToMessageEntity");
			MessageEntity me = messageDAO.getDefaultMessage();
			System.out.println("getDefaultMessage");
			if (me == null) {
				System.out.println("insertDefaultMessage");

				me=messageDAO.insertDefaultMessage(messageEntity);
			} else {
				System.out.println("updateDefaultMessage");

				me=messageDAO.updateDefaultMessage(messageEntity);
			}
			mr.setNotificationMessage(me.``getMessage());
			mr.setStatus("SUCCESS");

		} catch (MessageException e) {
			mr.setErrorCode("101");
			mr.setErrorMessage(e.getMessage());
			mr.setStatus("ERROR");
		} catch (Exception e) {
			mr.setErrorCode("101");
			mr.setErrorMessage("Error in Updating Message");
			mr.setStatus("ERROR");
		}
		return mr;
	}

	public Message convertMessageEntityToMessage(MessageEntity messageEntity) {
		Message message = new Message();
		message.setMessage(messageEntity.getMessage());
		message.setMessageId(messageEntity.getMessageId());
		return message;
	}

	public MessageEntity convertMessageToMessageEntity(Message message, String username) {
		MessageEntity me = new MessageEntity();
		if (message.getMessageId() != null)
			me.setMessageId(new Long(message.getMessageId()));
		me.setMessage(message.getMessage());
		me.setMessageType("DEFAULT");
		me.setCreatedBy(username);
		return me;
	}

}
