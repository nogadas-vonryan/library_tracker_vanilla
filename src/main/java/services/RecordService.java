package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
