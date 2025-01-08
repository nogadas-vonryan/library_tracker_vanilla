package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import lombok.Getter;
import repository.UserRepository;

@Getter
public class User {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	public int id;
	public String referenceNumber;
	public String firstName;
	public String lastName;
	public String password;
	public String role;
	public String dateCreated;
	
	public User(String referenceNumber, String firstName, String lastName, String password) {
		this.referenceNumber = referenceNumber;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = "USER";
	}
	
	public User(ResultSet rs) {		
		try {
            this.id = rs.getInt("id");
            this.referenceNumber = rs.getString("reference_number");
            this.firstName = rs.getString("first_name");
            this.lastName = rs.getString("last_name");
            this.password = rs.getString("password");
            this.role = rs.getString("role");
            this.dateCreated = rs.getString("date_created");
            
        } catch (Exception e) {
        	logger.severe(e.getMessage());
        }
	}
	
	public void save(Connection conn) throws SQLException {
		UserRepository userRepository = new UserRepository();
		
		if (this.id == 0) {
			userRepository.insert(conn, this);
		} else {
			userRepository.update(conn, this);
		}
	}
}
