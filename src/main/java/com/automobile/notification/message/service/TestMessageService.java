package com.automobile.notification.message.service;

import com.automobile.notification.message.model.MessageResponse;
import com.automobile.notification.message.model.TestMessage;

public interface TestMessageService {
	public MessageResponse sendTestMessage(TestMessage testMessage);
}
