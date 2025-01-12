package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import models.BorrowingRecord;
import utils.FileLogger;

public class RecordService {
	public static boolean isExpired(String date) {
		Logger logger = FileLogger.getLogger(RecordService.class);
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
	
	public static long borrowCount(List<BorrowingRecord> records) {
		return records.size();
	}
	
	public static long returnCount(List<BorrowingRecord> records) {
		return records.stream().filter(record -> record.isReturned()).count();
	}
	
	public static long expiredCount(List<BorrowingRecord> records) {
		return records.stream().filter(record -> RecordService.isExpired(record.getReturnDate())).count();
	}
}
