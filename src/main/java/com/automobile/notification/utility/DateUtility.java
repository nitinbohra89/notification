package com.automobile.notification.utility;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.log4j.Logger;

public class DateUtility {
	final static Logger logger = Logger.getLogger(DateUtility.class);

	public static boolean checkDatePattern(String dateFormat, String data) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			System.out.println("format--" + format);
			format.parse(data);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static LocalDate convertStringToDate(String dateFormat, String data) throws ParseException {
		logger.debug("Data----" + data + "    dateformat--" + dateFormat);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		LocalDate date = LocalDate.parse(data, formatter);
		return date;
	}

	/*public static void main(String[] args) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate date = LocalDate.parse("20/05/2015", formatter);
			System.out.println(date.getYear() + "   " + date.getDayOfMonth() + "   " + date.getMonthValue());
		} catch (DateTimeParseException exc) {
			throw exc; // Rethrow the exception.
		}
	}*/
}
