package trial1;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Use 'cj' for latest MySQL
            con = DriverManager.getConnection("jdbc:mysql://localhost:3300/movie_db", "root", "");
        } catch (Exception e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
        return con;
    }
}
