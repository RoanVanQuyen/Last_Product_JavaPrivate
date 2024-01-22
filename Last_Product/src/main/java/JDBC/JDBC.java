package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Last_Product" ;
        String user = "root" ;
        String password = ""  ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            return DriverManager.getConnection(url, user, password) ;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
