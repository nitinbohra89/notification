package com.automobile.notification.utility;

import java.util.Random;

public class GenerateOTPUtility {

	public static String generateOTP() {
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

}
