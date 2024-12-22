package authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.LoginDao;
import model.User;

import java.io.IOException;
import java.security.MessageDigest;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginDao userDao = new LoginDao();

    public RegisterServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("index.jsp?error=Please+fill+all+fields");
            return;
        }

        try {
            // Hash the password using MD5
            String hashedPassword = md5(password);

            // Create a User object
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            
            user.setPassword(password);

            // Save the user to the database
            boolean isUserRegistered = userDao.registerUser(user);

            if (isUserRegistered) {
                // Registration successful
                response.sendRedirect("login.jsp?message=Registration+successful.+Please+log+in");
            } else {
                // Registration failed
                response.sendRedirect("index.jsp?error=Registration+failed.+Please+try+again");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=An+error+occurred.+Please+try+again");
        }
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
