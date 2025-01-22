package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.PasswordRequest;
import utils.ResultHandler;

public class PasswordRequestRepository {
	public void insert(Connection conn, PasswordRequest request) throws SQLException {
		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO password_request (user_id) VALUES (?)");
		stmt.setInt(1, request.getUser().getId());
		stmt.executeUpdate();
	}
	
	public void delete(Connection conn, int id) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM password_request WHERE user_id = ?");
		stmt.setInt(1, id);
		stmt.executeUpdate();
	}
	
	public List<PasswordRequest> findAll(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM password_request");
        ResultSet rs = stmt.executeQuery();
        return ResultHandler.getResultList(conn, PasswordRequest.class, rs);
	}
	
	public PasswordRequest findByUserId(Connection conn, int userId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM password_request WHERE user_id = ?");
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        return ResultHandler.getResult(conn, PasswordRequest.class, rs);
	}
	
	public List<PasswordRequest> search(Connection conn, String searchTerm) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT r.* "
			    		+ "FROM password_request r "
			    		+ "JOIN user u ON r.user_id = u.id "
			    		+ "WHERE u.first_name LIKE ? "
			    		+ "   OR u.last_name LIKE ? "
			    		+ "   OR u.reference_number LIKE ? ");
		stmt.setString(1, "%" + searchTerm + "%");
		stmt.setString(2, "%" + searchTerm + "%");
		stmt.setString(3, "%" + searchTerm + "%");
		ResultSet rs = stmt.executeQuery();
		List<PasswordRequest> requests = ResultHandler.getResultList(conn, PasswordRequest.class, rs);
		return requests;
	}
}
