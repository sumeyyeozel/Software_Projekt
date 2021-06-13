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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import software_projekt.Software_Projekt;


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
    private Button init_weiter;
    @FXML
    private Label begincheck;
    String answer;

    @FXML
    private void InitBegin(MouseEvent event) throws IOException {
                  
                  String message = "STRT|cabinet 1|Nurdanseker|ADMIN|10|3";
		  System.out.println("===>> " + message);
                  Software_Projekt.toCabinet.println(message);
                  answer=Software_Projekt.fromCabinet.readLine();
                  System.out.println(answer);
		
                //Software_Projekt sp= new Software_Projekt();
               //Software_Projekt.connect2Cabinet(message);
		
                //setLabel();
                if (answer!=null&&answer.startsWith("Cabinet")) {
                    
                   begincheck.setTextFill(Color.GREEN);
                    begincheck.setText("Schrankverbindung : Erfolgreich");
                     } 
                else {
                begincheck.setTextFill(Color.TOMATO);
                begincheck.setText("Schrank konnte nicht verbunden werden : Fehler");}
                
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
       

    } 
    private void setLblError(Color color, String text) {
        begincheck.setTextFill(color);
        begincheck.setText(text);
        System.out.println(text);
    }
    
    /*public void setLabel(){
 
    }*/

    @FXML
    private void Init_Weiter(MouseEvent event) throws IOException {
        
        
        if (event.getSource() == init_weiter) {
        
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/software_projekt/fxml/Check_in1.fxml")));
                    stage.setScene(scene);
                    stage.show();

               
        }
        
        
        
        
    }
    
}
