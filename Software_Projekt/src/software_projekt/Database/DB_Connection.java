/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sumey
 */
public class DB_Connection {
    Connection myconn = null;
  public static Connection conDB() throws ClassNotFoundException {
      try {
            Connection con = DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11409731?user=sql11409731&&password=Dz2us1dZVi");
            return con;
        } catch (SQLException ex) {
            System.err.println("ConnectionUtil : "+ ex.getMessage());
           return null;
        }   
        
    }
  
}
