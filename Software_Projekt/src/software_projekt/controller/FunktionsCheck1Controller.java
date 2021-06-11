/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import software_projekt.Database.DB_Connection;
import software_projekt.Software_Projekt;


/**
 *
 * @author beyzaesra
 */
public class FunktionsCheck1Controller  implements Initializable {

    @FXML
    private ImageView button_home;
    @FXML
    private ImageView button_abbrechen;
    Connection conn = null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    Statement statement = null;
    int slot=1;
    String bauteil_id;
    Vector<String>messages = new Vector<String>();
     boolean first =true;
            float firstTargetTemp=70.5f;
            int firstTargetTime = 1;
            float secondTargetTemp= -25.8f;
            int secondTargetTime = 5;
    @FXML
    private Label funktionscheck_label;
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
       // messages.add("STRT|Kabinett 1|Nurdanseker|ADMIN|10|3") ;
        try {
            try {
                System.out.println("initialize basarili");
                
                conn = DB_Connection.conDB();
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT bauteil_id FROM ModelTable");
                while (resultSet.next()) {
                    bauteil_id = resultSet.getString(1);
                    messages.add("INIT|"+slot+"|"+bauteil_id+"");
                    System.out.println(bauteil_id);
                    slot++;
                    
                    
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(FunktionsCheck1Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FunktionsCheck1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            messages.add("ENDINIT");
            messages.add("STRTPRE|25"); // |failurrate [%1]
            for(int i= 1;i<slot; i++){
                messages.add("PRETST|"+i);
                messages.add("PRETST|"+i);
                messages.add("PRETST|"+i);
            }
            messages.add("ENDPRE");
            messages.add("STRTBURNIN");
            messages.add("OPERTEMP");
            messages.add("SETTARGET|"+ firstTargetTemp+"|"+ firstTargetTime+"|3|10"); 
            messages.add("STRTPING|25"); // |Failurerate[%]
            for(int j = 0; j<3; j++){
                for(int i =1;i< slot;i++){
                    messages.add("PING|"+i);
                    messages.add("PING|"+i);
                    messages.add("PING|"+i);
                }
            }
            messages.add("STOPPING");
            messages.add("SETTARGET|" + secondTargetTemp+"|"+ secondTargetTime+"|3|10");  
            messages.add("STRTPING|25"); // |Failurerate[%]
            for(int j = 0; j<5; j++){
                for(int i =1;i< slot;i++){
                    messages.add("PING|"+i);
                    messages.add("PING|"+i);
                    messages.add("PING|"+i);
                }
            }
            messages.add("STOPPING");
            
            
            
            //messages.add("SETTARGET|20|30|3|5");
            messages.add("STOP");
         
            Software_Projekt.test(Software_Projekt.socket,Software_Projekt.toServer,messages);
            if (Software_Projekt.answer.startsWith("<<===STOPPING - Cabinet will shutdown in a few moments. Goodbye!")) {
            funktionscheck_label.setTextFill(Color.TOMATO);
            funktionscheck_label.setText("test failed");
        } else {
            funktionscheck_label.setTextFill(Color.GREEN);
            funktionscheck_label.setText("TEST WAS SUCCESSFULLY");
        }
        
        
        
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(FunktionsCheck1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void funktionscheck_label(Color color, String text) {
        funktionscheck_label.setTextFill(color);
        funktionscheck_label.setText(text);
        System.out.println(text);
    }
    

}
