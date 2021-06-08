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
    private Button init_weiter;
    @FXML
    private Label begincheck;
    String answer;

    @FXML
    private void InitBegin(MouseEvent event) throws IOException {
                
                
		String message = "STRT|Kabinett 1|Nurdanseker|ADMIN|10|3";
		System.out.println("===>> " + message);
                //Software_Projekt sp= new Software_Projekt();
               //Software_Projekt.connect2Cabinet(message);
		Software_Projekt.toCabinet.println(message);
                String answer=Software_Projekt.fromCabinet.readLine();
                System.out.println(answer);
                setLabel();
                 
        
                
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
    
    public void setLabel(){
    if (answer!=null&&answer.startsWith("Cabinet")) {
                 begincheck.setTextFill(Color.GREEN);
                     begincheck.setText("Cabinet connection : Successful");
                     } else {
                begincheck.setTextFill(Color.TOMATO);
                begincheck.setText("Cabinet could not be connected: Fail ");}
    }

    @FXML
    private void Init_Weiter(MouseEvent event) throws IOException {
        
        
        if (event.getSource() == init_weiter) {
            //login here
            //if (init_weiter().equals("Success")) {
              //  try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/software_projekt/fxml/Check_in1.fxml")));
                    stage.setScene(scene);
                    stage.show();

               // } catch (IOException ex) {
                 //   System.err.println(ex.getMessage());
                //}

            //}
        }
        
        
        
        
    }
    
}
