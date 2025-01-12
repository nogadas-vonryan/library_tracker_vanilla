package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import models.PasswordRequest;
import utils.FileLogger;
import utils.ResultHandler;

public class PasswordRequestRepository {
	public void insert(Connection conn, PasswordRequest request) throws SQLException {
		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO password_request (user_id) VALUES (?)");
		stmt.setInt(1, request.getUser().getId());
		stmt.executeUpdate();
	}
	
	public void delete(Connection conn, int id) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM password_request WHERE id = ?");
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
}
