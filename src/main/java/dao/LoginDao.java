package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class LoginDao {
    private static final String URL = "jdbc:mysql://localhost:3306/servlet2012202401"; // Update this with your DB details
    private static final String USER = "root"; // Update this with your DB username
    private static final String PASSWORD = "pintu123"; // Update this with your DB password

    public User validate(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                // Set the parameters for the query
                statement.setString(1, email);
                statement.setString(2, password);

                // Execute the query and map the result to a User object
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPassword(resultSet.getString("password"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        // Check if email already exists
        String emailCheckSql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (PreparedStatement checkStatement = connection.prepareStatement(emailCheckSql)) {
                checkStatement.setString(1, user.getEmail());
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    return false;  // Email already exists
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword());

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0; // Return true if insertion was successful
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
