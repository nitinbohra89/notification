package com.automobile.notification.utility;

import java.security.ProviderException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
public class SMSClientUtility {
	final static Logger logger = Logger.getLogger(SMSClientUtility.class);
	
    public static boolean sendPostMessage(String url,Map<String,String> body) throws ProviderException{
		try {
			logger.debug("sendPostMessage:: START");
			Gson gson = new Gson(); 
			Client client = Client.create();

			WebResource webResource = client.resource(url);
			String input = gson.toJson(body);
			logger.debug("sendPostMessage:: Input JSON--"+input);
			logger.debug("sendPostMessage:: Input URL--"+url);
		
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			  logger.debug("JSON Output ---"+output);
			return true;
		  } catch (Exception e) {

			  logger.debug("Error----"+e.getMessage());
			  throw new ProviderException("Error in sending Message");

		  }

		} 
	}
	