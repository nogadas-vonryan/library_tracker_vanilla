package models;

import java.sql.Connection;
import java.sql.ResultSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyRecord {
	String month;
	int count;
	
	public MonthlyRecord(Connection conn, ResultSet rs) {
		try {
            this.month = rs.getString("month");
            this.count = rs.getInt("number");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
