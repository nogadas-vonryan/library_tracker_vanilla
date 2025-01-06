package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import utils.FileLogger;

public class UserRepository {
	
	public Logger logger = FileLogger.getLogger(this.getClass().getName());
	
	public ResultSet findByReferenceNumber(Connection conn, String referenceNumber) {
		try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE reference_number = ?");
            stmt.setString(1, referenceNumber);
            ResultSet rs = stmt.executeQuery();
            return rs;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
	}
}
