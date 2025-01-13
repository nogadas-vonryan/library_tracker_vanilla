package servlets;

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

@WebServlet("/admin/password-requests/*")
public class PasswordChangeServlet extends BaseServlet {
	private static final long serialVersionUID = 12L;
	
	PasswordRequestRepository passwordRequestRepository = new PasswordRequestRepository();
	UserRepository userRepository = new UserRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/password-requests/*", "GET");
		
		if (!Auth.isLoggedIn(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		
		String path = req.getPathInfo();
		if (path == null) {
			try {
				resp.sendRedirect("/admin/password-requests");
			} catch (Exception e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
			return;
		}

		String[] parts = path.split("/");
		String id = parts[1];
		
		try {
			PasswordRequest request = passwordRequestRepository.findByUserId(conn, Integer.parseInt(id));
			
			if (request == null) {
				resp.sendRedirect("/admin/password-requests?error=RequestNotFound");
				return;
			}
			
			req.setAttribute("request", request);
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
        
        try {
        	User user = userRepository.findByReferenceNumber(conn, referenceNumber);
			
        	if (user == null) {
				resp.sendRedirect("/admin/password-requests?error=UserNotFound");
				return;
			}
        	
     		if (!newPassword.equals(confirmPassword)) {
     			try {
     				resp.sendRedirect("/admin/password-requests/" + user.id + "/?error=PasswordMismatch");
		    	}
		    	catch (Exception e) {
		    		LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		    	}
		    	return;
		    }
			
			user.setPassword(newPassword);
			user.save(conn);
			
			passwordRequestRepository.delete(conn, user.id);
			
			LoggerManager.logTransaction(req, "Update User ID: " + user.getId());
			LoggerManager.logTransaction(req, "Delete Password Request's USER ID: " + user.id);

        	resp.sendRedirect("/admin/password-requests?success=PasswordChanged");
        } catch (Exception e) {
        	LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
        }
	}
}
