package runners;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import database.DatabaseConnection;
import repository.UserRepository;
import utils.LoggerManager;

import models.User;

public class HashAllPasswords {
	public static void main(String[] args) {
		Connection conn = DatabaseConnection.getConnection();
		
		UserRepository userRepository = new UserRepository();
		List<User> users = null;
		try {
			users = userRepository.findAll(conn);
			
			users.forEach(user -> {
				String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
				user.setPassword(hashedPassword);
				
				try {
					user.save(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			conn.close();
		} catch (SQLException e) {
			LoggerManager.systemLogger.severe(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("Successfully hashed all passwords!");
	}
}
