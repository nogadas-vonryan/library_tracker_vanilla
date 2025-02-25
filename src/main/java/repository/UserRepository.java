package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.User;
import utils.ResultHandler;

public class UserRepository {

	public void insert(Connection conn, User user) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO user (reference_number, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?)");
		stmt.setString(1, user.referenceNumber);
		stmt.setString(2, user.firstName);
		stmt.setString(3, user.lastName);
		stmt.setString(4, user.password);
		stmt.setString(5, user.role);
		stmt.executeUpdate();
	}

	public void update(Connection conn, User user) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(
				"UPDATE user SET reference_number = ?, first_name = ?, last_name = ?, password = ? WHERE id = ?");
		stmt.setString(1, user.referenceNumber);
		stmt.setString(2, user.firstName);
		stmt.setString(3, user.lastName);
		stmt.setString(4, user.password);
		stmt.setInt(5, user.id);
		stmt.executeUpdate();
	}
	
	public List<User> findAll(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user");
        ResultSet rs = stmt.executeQuery();
        List<User> users = ResultHandler.getResultList(conn, User.class, rs);

        return users;
	}

	public User findById(Connection conn, int id) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		User user = ResultHandler.getResult(conn, User.class, rs);

		return user;
	}

	public User findByReferenceNumber(Connection conn, String referenceNumber) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE reference_number = ?");
		stmt.setString(1, referenceNumber);
		ResultSet rs = stmt.executeQuery();
		User user = ResultHandler.getResult(conn, User.class, rs);

		return user;
	}
}
