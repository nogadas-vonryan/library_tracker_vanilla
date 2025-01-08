package servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.Auth;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
	
	private static final long serialVersionUID = 3L;
	private Auth auth = new Auth();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			forward(req, resp, "login");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String referenceNumber = req.getParameter("referenceNumber");
		String password = req.getParameter("password");	
		
	   try {
	        boolean isAuthenticated = auth.login(this, req, referenceNumber, password);
	        
			if(!isAuthenticated) {
				resp.sendRedirect("login?error=InvalidCredentials");
				return;
			}
			
			if(req.getSession().getAttribute("role").equals("ADMIN")) {
                resp.sendRedirect("admin/books");
            }
			else {	
				resp.sendRedirect("user/books");
			}
	        
	    } catch (Exception e) {
	        logger.severe(e.getMessage());
	    }
	}
	
}
