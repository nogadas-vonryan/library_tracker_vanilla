package servlets;

import java.sql.SQLException;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.Auth;
import utils.LoggerManager;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
	
	private static final long serialVersionUID = 3L;
	private Auth auth = new Auth();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (Auth.isLoggedIn(req)) {
			if (Auth.isAdmin(req)) {
				handleRedirect(resp, "/admin/books");
			} else {
				handleRedirect(resp, "/user/books");
			}
			return;
		}
		
		try {
			forward(req, resp, "login");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String referenceNumber = req.getParameter("referenceNumber");
		String password = req.getParameter("password");	
		
        boolean isAuthenticated = false;
		try {
			isAuthenticated = auth.login(this, req, referenceNumber, password);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			handleRedirect(resp, "/login?error=InvalidCredentials");
		}
        
		if(!isAuthenticated) {
			LoggerManager.accessLogger.info("User: " + req.getRemoteAddr() + " Failed to login: Invalid Credentials");
			handleRedirect(resp, "/login?error=InvalidCredentials");
			return;
		}
		
		String role = (String) req.getSession().getAttribute("role");
		
		if(role == null) {
			LoggerManager.accessLogger.info("User: " + req.getRemoteAddr() + " Failed to login: Role is null");
			handleRedirect(resp, "/login?error=InvalidRole");
			return;
		}
		else if(role.equals("ADMIN")) {
            handleRedirect(resp, "/admin/books");
            return;
        }
		else {	
			handleRedirect(resp, "/user/books");
			return;
		}
	}
	
}
