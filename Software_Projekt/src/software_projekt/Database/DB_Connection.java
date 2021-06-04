/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import software_projekt.Software_Projekt;
import software_projekt.controller.FXMLDocumentController;


/**
 *
 * @author sumey
 */
public class DB_Connection {
    Connection myconn = null;
    String email=null;
     String userName;
    
    
  public  static Connection conDB() throws ClassNotFoundException {
      try {
            Connection myconn = DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11412729?user=sql11412729&&password=r1pEpnJnYx");
            return myconn;
        } catch (SQLException ex) {
            System.err.println("ConnectionUtil : "+ ex.getMessage());
           return null;
        }   
        
    }
  
  //cuma gunu derleme amaciyla ayzildi


  
  
  
  
  
}
