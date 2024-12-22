package authentication;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession(false); // Get the session, don't create a new one
	    if(session !=null)
	    {
	     session.invalidate(); // Destroy the session
	    }
	    response.sendRedirect("login.jsp?message=You+have+been+logged+out+successfully");

	    }
}
