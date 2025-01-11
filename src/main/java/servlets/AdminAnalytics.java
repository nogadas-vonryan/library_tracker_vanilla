package servlets;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.MonthlyRecord;
import repository.BorrowingRecordRepository;
import services.Auth;

@WebServlet("/admin/analytics")
public class AdminAnalytics extends BaseServlet {
	
	private static final long serialVersionUID = 14L;
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		String yearString = req.getParameter("year");
		Integer year = LocalDate.now().getYear();
		
		if (yearString != null) year = Integer.parseInt(yearString);
		
		List<MonthlyRecord> records = borrowingRecordRepository.getRecordAnalytics(conn, year);
		req.setAttribute("records", records);
		
		try {
			forward(req, resp, "admin-analytics");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

}
