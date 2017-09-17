package com.automobile.notification.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
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
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.parse(data);

	}
}
