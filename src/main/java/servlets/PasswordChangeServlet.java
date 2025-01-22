package servlets;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.PasswordRequest;
import models.User;
import repository.PasswordRequestRepository;
import repository.UserRepository;
import services.Auth;
import utils.LoggerManager;

@WebServlet("/admin/password-requests/*")
public class PasswordChangeServlet extends BaseServlet {
	private static final long serialVersionUID = 12L;
	
	PasswordRequestRepository passwordRequestRepository = new PasswordRequestRepository();
	UserRepository userRepository = new UserRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/password-requests/*", "GET");
		
		if (!Auth.isLoggedIn(req)) {
			handleRedirect(resp, "/login");
		}
		
		String path = req.getPathInfo();
		if (path == null) {
			handleRedirect(resp, "/admin/password-requests");
			return;
		}

		String[] parts = path.split("/");
		String id = parts[1];
		
		PasswordRequest request = null;
		try {
			request = passwordRequestRepository.findByUserId(conn, Integer.parseInt(id));
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		if (request == null) {
			handleRedirect(resp, "/admin/password-requests");
			return;
		}
		
		req.setAttribute("request", request);
		
		try {
			forward(req, resp, "admin-password-change");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String referenceNumber = req.getParameter("referenceNumber");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        
    	User user = null;
		try {
			user = userRepository.findByReferenceNumber(conn, referenceNumber);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
    	if (user == null) {
			handleRedirect(resp, "/admin/password-requests?error=UserNotFound");
			return;
		}
    	
    	Pattern referenceNumberPattern = Pattern.compile("\\d{4}-\\d{5}-[A-Z]{2}-\\d");
		Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)[A-Za-z\\W\\d]{8,}$");
		
		if (!passwordPattern.matcher(newPassword).matches()) {
			LoggerManager.accessLogger.info("User: " + req.getRemoteAddr() + " Failed to register: Invalid Password");
			handleRedirect(resp, "/admin/password-requests/" + user.id + "/?error=PasswordInvalid");
			return;
		}
    	
 		if (!newPassword.equals(confirmPassword)) {
			handleRedirect(resp, "/admin/password-requests/" + user.id + "/?error=PasswordMismatch");
	    	return;
	    }
		
 		String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
 		
		user.setPassword(hashedPassword);
		try {
			user.save(conn);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		try {
			passwordRequestRepository.delete(conn, user.id);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		LoggerManager.logTransaction(req, "Update User ID: " + user.getId());
		LoggerManager.logTransaction(req, "Delete Password Request's USER ID: " + user.id);

    	handleRedirect(resp, "/admin/password-requests?success=PasswordChanged");
	}
}
