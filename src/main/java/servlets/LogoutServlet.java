package servlets;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {

	private static final long serialVersionUID = 12L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getSession().invalidate();
            resp.sendRedirect("/login");
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
	}
	
}
