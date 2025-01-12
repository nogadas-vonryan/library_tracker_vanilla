package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class DateExpiry {
	public static boolean isExpired(String date) {
		Logger logger = FileLogger.getLogger(DateExpiry.class);
		boolean isExpired = false;
		
		try {
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			Date currentDate = new Date();
			
			if (currentDate.after(returnDate)) {
				isExpired = true;
			}
			
		} catch (ParseException e) {
			logger.severe(e.getMessage());
		}
		
		return isExpired;
	}
}
