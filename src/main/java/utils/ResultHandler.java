package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultHandler<T> {
	
	public static <T> T getResult(Connection conn, Class<T> model, ResultSet rs) {
		try {
			if (rs.next()) {
				try {
					T item = model.getConstructor(Connection.class, ResultSet.class).newInstance(conn, rs);
					return item;
				} catch (Exception e) {
					System.out.println("error initializing model");
					LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
				}
			}

		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);		
		}
		
		return null;
	}
	
	public static <T> List<T> getResultList(Connection conn, Class<T> model, ResultSet rs) {
		List<T> list = new ArrayList<T>();
		
		try {
			while (rs.next()) {
				try {
					list.add(model.getConstructor(Connection.class, ResultSet.class).newInstance(conn, rs));
				} catch (Exception e) {
					LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);				
				}
			}
			
			return list;
			
		} catch (SQLException e) {
			System.out.println("error getting result list from database");
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return null;
	}
}
