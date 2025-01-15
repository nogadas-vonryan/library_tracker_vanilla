package servlets;

import java.sql.SQLException;
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
		
		List<PasswordRequest> requests = null;
		try {
			requests = passwordRequestRepository.findAll(conn);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		for (PasswordRequest request : requests) {
			System.out.println(request.getUser().getFirstName() + request.getUser().getLastName() + request.getDateTimeCreated());
		}
		
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
			handleRedirect(resp, "/admin/password-requests?success=RequestDeleted");
		} else if(method.equals("POST")) {
			insert(req, resp);
			handleRedirect(resp, "/admin/password-requests?success=RequestCreated");
		} else {
			handleRedirect(resp, "/login");
		}
	}

	private void insert(HttpServletRequest req, HttpServletResponse resp) {
		String referenceNumber = req.getParameter("referenceNumber");
		User user;
		try {
			user = userRepository.findByReferenceNumber(conn, referenceNumber);
			PasswordRequest request = new PasswordRequest(user);
			request.save(conn);
			
			LoggerManager.logTransaction(req, "Add Password Request ID");
			
			resp.sendRedirect("/login?success=PasswordRequestSent");
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
