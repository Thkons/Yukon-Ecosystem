/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hos;

/**
 *
 * @author Thanasis
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");
    }
}
