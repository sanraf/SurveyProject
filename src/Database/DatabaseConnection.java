/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author acer
 */
public class DatabaseConnection {
    
    public static Connection getConn(){
        
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey","root","1234");
            return connection;
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.print(e);
            return null;
        }
    }
    
}
