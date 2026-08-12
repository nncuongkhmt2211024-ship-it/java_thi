package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connect() {
        Connection connection = null;

        try {
            // 1. Load JDBC Driver cho PostgreSQL
            Class.forName("org.postgresql.Driver");

            // 2. URL kết nối
            String url = "jdbc:postgresql://localhost:5432/db_library_management";

            // 3. Tài khoản đăng nhập PostgreSQL
            String user = "postgres";
            String password = "m01668516412"; // Thay mật khẩu của bạn ở đây

            // 4. Thực hiện kết nối
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy JDBC Driver PostgreSQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối CSDL: " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }
}