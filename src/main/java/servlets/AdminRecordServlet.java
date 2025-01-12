package servlets;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import repository.BorrowingRecordRepository;
import repository.UserRepository;
import services.Auth;
import utils.DateExpiry;

@WebServlet("/admin/records")
public class AdminRecordServlet extends BaseServlet {

	private static final long serialVersionUID = 2L;
	
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
		
		try {
			String search = req.getParameter("search");
			String status = req.getParameter("status");
			String month = req.getParameter("month");
			String year = req.getParameter("year");
			String sortOrder = req.getParameter("sortOrder");
			
			if (search == null) search = "";
			
			List<BorrowingRecord> records = borrowingRecordRepository.search(conn, search);
			
			records = records.stream()
					.filter(record -> status == null || (status.equals("returned") && record.isReturned()) || (status.equals("borrowing") && !record.isReturned()) || status.equals("expired") && DateExpiry.isExpired(record.getReturnDate()))                              
					.filter(record -> month == null || LocalDate.parse(record.getBorrowDate()).getMonthValue() == Integer.parseInt(month))
					.filter(record -> year == null || LocalDate.parse(record.getBorrowDate()).getYear() == Integer.parseInt(year))
					.sorted((record1, record2) -> {
				        LocalDate date1 = LocalDate.parse(record1.getBorrowDate());
				        LocalDate date2 = LocalDate.parse(record2.getBorrowDate());
				        
				        if (sortOrder != null && sortOrder.equals("asc")) {
				            return date1.compareTo(date2);
				        } else {
				            return date2.compareTo(date1);
				        }
				    })
					.toList();
			
			req.setAttribute("records", records);
			forward(req, resp, "admin-records");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		String method = req.getParameter("_method");
		
		try {
			switch(method) {					
				case "PUT":
					BorrowingRecord record = borrowingRecordRepository.findById(conn, Integer.parseInt(req.getParameter("recordId")));
					record.setReturned(req.getParameter("isReturned").equals("true") ? true : false);
					record.save(conn);
					resp.sendRedirect("/admin/records?success=RecordUpdated");
					break;
			
			    case "DELETE":
	                int id = Integer.parseInt(req.getParameter("id"));
	                borrowingRecordRepository.delete(conn, id);
	                resp.sendRedirect("/admin/records?success=RecordDeleted");
	                break;
	            default:
	            	resp.sendRedirect("/admin/records?error=InvalidMethod");
	                break;
			}
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	
}
