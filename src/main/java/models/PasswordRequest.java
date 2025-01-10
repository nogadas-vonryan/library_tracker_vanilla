package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import lombok.Getter;
import lombok.ToString;
import repository.UserRepository;

@Getter
@ToString
public class PasswordRequest {
	User user;
	String dateTimeCreated;
	
	public PasswordRequest(User user) {
		this.user = user;
	}
	
	public PasswordRequest(Connection conn, ResultSet rs) throws SQLException {
		UserRepository userRepository = new UserRepository();
        
        this.user = userRepository.findById(conn, rs.getInt("user_id"));
        this.dateTimeCreated = rs.getString("datetime_created");
    }
	
	public void save(Connection conn) throws SQLException {
		repository.PasswordRequestRepository passwordRequestRepository = new repository.PasswordRequestRepository();
        passwordRequestRepository.insert(conn, this);
	}
}
