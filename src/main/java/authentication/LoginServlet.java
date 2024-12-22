package authentication;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.LoginDao;
import model.User;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginDao loginDao = new LoginDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=Please+enter+both+email+and+password");
            return;
        }

        // Authenticate user and get User object
        User user = loginDao.validate(email, password);
        if (user != null) {
            // Store user information in the session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect to the welcome page
            response.sendRedirect("welcome.jsp");
        } else {
            // Redirect back to login with an error
            response.sendRedirect("login.jsp?error=Invalid+email+or+password");
        }
    }
}
