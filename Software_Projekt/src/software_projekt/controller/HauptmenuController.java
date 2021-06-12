/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sumey
 */
public class HauptmenuController implements Initializable {
    ObservableList betriebList=FXCollections.observableArrayList();
     //Hauptmenu 
    @FXML
    private Button h_weiter;
    @FXML
    private ChoiceBox<String> h_choice;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
        
    }   
    @FXML
    private void displayValue(ActionEvent event){
        String b_list= h_choice.getValue();
         if (event.getSource() == h_weiter) {
            //login here
            if (b_list=="Testbetrieb") {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/software_projekt/fxml/setup.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
         }
         
        
    }
    private void loadData(){
     betriebList.removeAll(betriebList);
     String first = "Testbetrieb";
     String second = "Handbetrieb";
     
     betriebList.addAll(first, second);
     h_choice.getItems().addAll(betriebList);
    }
}
