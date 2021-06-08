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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software_projekt.ModelTable;



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
  public static ObservableList<ModelTable> getDataUsers() {
      Connection conn = null;
        try {
            conn = conDB();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
      ObservableList<ModelTable> list = FXCollections.observableArrayList();
      try{
       PreparedStatement ps=conn.prepareStatement("select * from ModelTable");
       ResultSet rs=ps.executeQuery();
       while(rs.next()){
         list.add(new ModelTable(Integer.parseInt(rs.getString("id")), rs.getString("username"), rs.getString("e_mail"), rs.getString("bauteil_id")));
       
       
       }
      
      }
      catch(Exception e){
      
      }
        return list;
  
  }
  //cuma gunu derleme amaciyla ayzildi


  
  
  
  
  
}
