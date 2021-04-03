/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author sumey
 */
public class FXMLDocumentController implements Initializable {
    //Hauptmenu choicebox list
    ObservableList<String> h_choicelist= FXCollections.
            observableArrayList("Testbetrieb", "Handbetrieb");
    
     //Anmeldung
    @FXML
    private Button anmelden;
    @FXML 
    private TextField benutzererk;
    @FXML 
    private PasswordField pass;
     //Hauptmenu 
    @FXML
    private Button h_weiter;
    @FXML
    private Button h_ende;
    @FXML
    private ChoiceBox h_choice;
    //SETUP
    @FXML
    private Button set_weiter;
    @FXML
    private Button set_abbrechen;
    @FXML
    private TextField set_tf_artikel;
    @FXML
    private TextField set_tf_auftrag;
    //initialisierung
    @FXML
    private Button init_beginn;
    @FXML
    private Button init_abbrechen;
    //initialisierung ende
    @FXML
    private Button iende_checkin;
    @FXML
    private Button iende_abbrechen;
    //initialisierun ende alert
    @FXML
    private Button ialert_abbrechen;
    
    //hauptmenu choicebox aktifleştirme ama oldu mu bilmiyorum.
    @FXML
    private void initialize(){
      h_choice.setValue("Testbetrieb");
      h_choice.setItems(h_choicelist);
      


}
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
