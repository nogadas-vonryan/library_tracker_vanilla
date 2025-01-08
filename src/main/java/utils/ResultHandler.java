package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ResultHandler<T> {
	public static <T> T getResult(Connection conn, Class<T> model, ResultSet rs) {
		Logger logger = FileLogger.getLogger("utils.ResultHandler");
		
		try {
			if (rs.next()) {
				try {
					T item = model.getConstructor(Connection.class, ResultSet.class).newInstance(conn, rs);
					return item;
				} catch (Exception e) {
					logger.severe(e.getMessage());
				}
			}

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
		return null;
	}
	
	public static <T> List<T> getResultList(Connection conn, Class<T> model, ResultSet rs) {
		Logger logger = FileLogger.getLogger("utils.ResultHandler");
		List<T> list = new ArrayList<T>();
		
		try {
			while (rs.next()) {
				try {
					list.add(model.getConstructor(Connection.class, ResultSet.class).newInstance(conn, rs));
				} catch (Exception e) {
					logger.severe(e.getMessage());
				}
			}
			
			return list;
			
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
		return null;
	}
}
