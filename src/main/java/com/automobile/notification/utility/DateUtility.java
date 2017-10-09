package com.automobile.notification.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtility {
	final static Logger logger = Logger.getLogger(DateUtility.class);

	public static boolean checkDatePattern(String dateFormat, String data) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			System.out.println("format--"+format);
			format.parse(data);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Date convertStringToDate(String dateFormat, String data) throws ParseException {
		logger.debug("Data----"+data+"    dateformat--"+dateFormat);
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		logger.debug("format.parse(data)-----"+format.parse(data));
		return format.parse(data);

	}
}
