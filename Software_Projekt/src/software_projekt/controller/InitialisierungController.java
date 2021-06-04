/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import software_projekt.Database.DB_Connection;
import software_projekt.Software_Projekt;
import static software_projekt.Software_Projekt.connect2Cabinet;
import static software_projekt.Software_Projekt.connect2Database;
import static software_projekt.Software_Projekt.toCabinet;
import static software_projekt.Software_Projekt.users;


/**
 * FXML Controller class
 *
 * @author sumey
 */


public class InitialisierungController implements Initializable {

        //initialisierung

    @FXML
    private Button init_beginn;
    @FXML
    private Button init_abbrechen;
    

    @FXML
    private void InitBegin(MouseEvent event) throws IOException {
                
                
		String message = "STRT|Kabinett 1|Nurdanseker|TESTER|10|3";
		System.out.println("===>> " + message);
                //Software_Projekt sp= new Software_Projekt();
               //Software_Projekt.connect2Cabinet(message);
		Software_Projekt.toCabinet.println(message);
               System.out.println(Software_Projekt.fromCabinet.readLine());
             
                
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
     
        
    }
    
}
