package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.PasswordRequest;
import models.User;
import repository.PasswordRequestRepository;
import repository.UserRepository;
import services.Auth;

@WebServlet("/admin/password-requests/*")
public class PasswordChangeServlet extends BaseServlet {
	private static final long serialVersionUID = 12L;
	
	PasswordRequestRepository passwordRequestRepository = new PasswordRequestRepository();
	UserRepository userRepository = new UserRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		String path = req.getPathInfo();
		if (path == null) {
			try {
				resp.sendRedirect("/admin/password-requests");
			} catch (Exception e) {
				logger.severe(e.getMessage());
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
			logger.severe(e.getMessage());
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
		    		logger.severe(e.getMessage());
		    	}
		    	return;
		    }
			
			user.setPassword(newPassword);
			user.save(conn);
			
        	resp.sendRedirect("/admin/password-requests?success=PasswordChanged");
        } catch (Exception e) {
        	logger.severe(e.getMessage());
        }
        
       
       
	}
}
