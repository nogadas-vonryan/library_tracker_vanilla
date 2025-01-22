package servlets;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MonthlyRecord;
import repository.BorrowingRecordRepository;
import services.Auth;
import utils.LoggerManager;

@WebServlet("/admin/analytics")
public class AdminAnalytics extends BaseServlet {
	
	private static final long serialVersionUID = 14L;
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/analytics", "GET");
		
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			handleRedirect(resp, "/login");
			return;
		}
		
		String yearString = req.getParameter("year");
		Integer year = LocalDate.now().getYear();
		
		if (yearString != null) year = Integer.parseInt(yearString);
		
		List<MonthlyRecord> records = null;
		try {
			records = borrowingRecordRepository.getRecordAnalytics(conn, year);	
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		int totalBorrowed = records.stream().mapToInt(record -> record.getCount()).sum();
		int averageBorrowed = (totalBorrowed > 0) ? totalBorrowed / records.size() : 0;
		
		req.setAttribute("year", year);
		req.setAttribute("totalBorrowed", totalBorrowed);
		req.setAttribute("averageBorrowed", averageBorrowed);
		req.setAttribute("records", records);
		
		try {
			forward(req, resp, "admin-analytics");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
