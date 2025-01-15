package servlets;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import repository.BorrowingRecordRepository;
import services.Auth;
import services.RecordService;
import utils.LoggerManager;

@WebServlet("/admin/records")
public class AdminRecordServlet extends BaseServlet {

	private static final long serialVersionUID = 2L;
	
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/records", "GET");
		
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			handleRedirect(resp, "/login");
		}
		
		String search = req.getParameter("search");
		String status = req.getParameter("status");
		String month = req.getParameter("month");
		String year = req.getParameter("year");
		String sortOrder = req.getParameter("sortOrder");
		
		if (search == null) search = "";
		
		List<BorrowingRecord> records = null;
		try {
			records = borrowingRecordRepository.search(conn, search);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		records = records.stream()
				.filter(record -> status == null || (status.equals("returned") && record.isReturned()) || (status.equals("borrowing") && !record.isReturned()) || status.equals("expired") && RecordService.isExpired(record))                              
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
		
		try {
			forward(req, resp, "admin-records");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			handleRedirect(resp, "/login");
			return;
		}
		
		String method = req.getParameter("_method");
		
		switch(method) {					
			case "PUT":
				handlePut(req, resp);
				break;
		
		    case "DELETE":
		    	handleDelete(req, resp);
                break;
            default:
            	LoggerManager.systemLogger.log(Level.WARNING, "Invalid Method");
                handleRedirect(resp, "/admin/records?error=InvalidMethod");
            	break;
		}
	}	
	
	private void handlePut(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/records", "PUT");
		
		BorrowingRecord record = null;
		try {
			record = borrowingRecordRepository.findById(conn, Integer.parseInt(req.getParameter("recordId")));
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage());
			handleRedirect(resp, "/admin/records?error=RecordNotFound");
			return;
		}
		
		record.setReturned(req.getParameter("isReturned").equals("true") ? true : false);
		try {
			record.save(conn);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage());
			handleRollback();
			return;
		}
		
		LoggerManager.logTransaction(req, "Update Record ID: " + record.getId());
		handleRedirect(resp, "/admin/records?success=RecordUpdated");
	}
	
	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/records", "DELETE");
    	
        int id = Integer.parseInt(req.getParameter("id"));
		try {
			borrowingRecordRepository.delete(conn, id);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage());
			handleRollback();
			return;
		}
        
        LoggerManager.logTransaction(req, "Delete Record ID: " + id);
        handleRedirect(resp, "/admin/records?success=RecordDeleted");
	}
}
