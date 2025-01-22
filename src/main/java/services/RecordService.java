package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.BorrowingRecord;
import utils.LoggerManager;

public class RecordService {
	public static boolean isExpired(String date) {
		boolean isExpired = false;
		
		try {
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			Date currentDate = new Date();
			
			if (currentDate.after(returnDate)) {
				isExpired = true;
			}
			
		} catch (ParseException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return isExpired;
	}
	
	public static boolean isExpired(BorrowingRecord record) {
		boolean isExpired = false;
		
		try {
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(record.returnDate);
			Date currentDate = new Date();
			
			if (!record.isReturned() && currentDate.after(returnDate)) {
				isExpired = true;
			}
			
		} catch (ParseException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return isExpired;
	}
	
	public static long daysLeftBeforeExpiry(BorrowingRecord record) {
		try {
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(record.returnDate);
			Date currentDate = new Date();
			long daysLeft = ChronoUnit.DAYS.between(currentDate.toInstant(), returnDate.toInstant());
			return daysLeft;
 		} catch (ParseException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return 0;
	}
	
	public static boolean isNearDueOrExpired(List<BorrowingRecord> records) {
		if (records == null || records.isEmpty()) {
			return false;
		}
		
		return records.stream().anyMatch(record -> isExpired(record) || daysLeftBeforeExpiry(record) <= 7);
	}
	
	public static long borrowCount(List<BorrowingRecord> records) {
		return records.size();
	}
	
	public static long returnCount(List<BorrowingRecord> records) {
		return records.stream().filter(record -> record.isReturned()).count();
	}
	
	public static long expiredCount(List<BorrowingRecord> records) {
		return records.stream().filter(record -> RecordService.isExpired(record)).count();
	}
}
