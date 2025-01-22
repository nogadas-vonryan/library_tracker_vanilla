package servlets;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.PasswordRequest;
import models.User;
import repository.PasswordRequestRepository;
import repository.UserRepository;
import services.Auth;
import utils.LoggerManager;

@WebServlet("/admin/password-requests")
public class PasswordRequestServlet extends BaseServlet {

	private static final long serialVersionUID = 11L;
	
	UserRepository userRepository = new UserRepository();
	PasswordRequestRepository passwordRequestRepository = new PasswordRequestRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/password-requests", "GET");
		
		if (!Auth.isLoggedIn(req)) {
			handleRedirect(resp, "/login");
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String search = req.getParameter("search");
		String month = req.getParameter("month");
		String year = req.getParameter("year");
		String sortOrder = req.getParameter("sortOrder");
		
		if (search == null) search = "";
		
		List<PasswordRequest> requests = null;
		try {
			requests = passwordRequestRepository.search(conn, search);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		requests = requests.stream()
				.filter(record -> month == null || LocalDate.parse(record.getDateTimeCreated(), formatter).getMonthValue() == Integer.parseInt(month))
				.filter(record -> year == null || LocalDate.parse(record.getDateTimeCreated(), formatter).getYear() == Integer.parseInt(year))
				.sorted((record1, record2) -> {
			        LocalDate date1 = LocalDate.parse(record1.getDateTimeCreated(), formatter);
			        LocalDate date2 = LocalDate.parse(record2.getDateTimeCreated(), formatter);
			        
			        if (sortOrder != null && sortOrder.equals("asc")) {
			            return date1.compareTo(date2);
			        } else {
			            return date2.compareTo(date1);
			        }
			    })
				.toList();
		
		req.setAttribute("requests", requests);
		
		try {
			forward(req, resp, "admin-password-requests");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {		
		String method = req.getParameter("_method");
		
		if (Auth.isLoggedIn(req) && Auth.isAdmin(req) && method.equals("DELETE")) {
			delete(req, resp);
			return;
		} else if(method.equals("POST")) {
			insert(req, resp);
			return;
		} else {
			handleRedirect(resp, "/login");
		}
	}

	private void insert(HttpServletRequest req, HttpServletResponse resp) {
		String referenceNumber = req.getParameter("referenceNumber");
		User user;
		try {
			user = userRepository.findByReferenceNumber(conn, referenceNumber);
			
			if (user == null) {
				resp.sendRedirect("/login?error=UserNotFound");
				return;
			}
			
			PasswordRequest existingRequest = passwordRequestRepository.findByUserId(conn, user.getId());
			
			if (existingRequest != null) {
				resp.sendRedirect("/login?error=RequestExists");
				return;
			}
			
			PasswordRequest request = new PasswordRequest(user);
			request.save(conn);
			
			LoggerManager.logTransaction(req, "Add Password Request ID");
			
			resp.sendRedirect("/login?success=PasswordRequestCreated");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		String id = req.getParameter("id");
		try {
			passwordRequestRepository.delete(conn, Integer.parseInt(id));
			
			LoggerManager.logTransaction(req, "Delete Password Request ID: " + id);
			
			resp.sendRedirect("/admin/password-requests?success=RequestDeleted");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
