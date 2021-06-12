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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.stage.Stage;
import software_projekt.Database.DB_Connection;

/**
 * FXML Controller class
 *
 * @author sumey
 */
public class SetupController implements Initializable {

        //SETUP
    @FXML
    private Button set_weiter;
    @FXML
    private TextField set_tf_artikel;
    @FXML
    private TextField set_tf_auftrag;
    
    @FXML
    private Label setLblError;
    @Override
       public void initialize(URL url, ResourceBundle rb) {
        // TODO
                System.out.println("succes");
        if (con == null) {
            setLblError.setTextFill(Color.TOMATO);
            setLblError.setText("Serverfehler : Pruefen");
        } else {
            setLblError.setTextFill(Color.GREEN);
            setLblError.setText("Server ist up : Gut zu gehen");
        }
    }    
    Connection con=null;
    ResultSet resultSet=null;
    PreparedStatement preparedStatement=null;
   
    
    
    
    @FXML
    private void enterWiter(MouseEvent event) {
        if (event.getSource() == set_weiter) {
            //login here
            if (weiter().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/software_projekt/fxml/initialisierung.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }
    
    
    
    
      public SetupController() throws ClassNotFoundException{
        
             con = DB_Connection.conDB();
        
    }
    
    
    private String weiter() {

        String status = "Success";
        String artikelnummer = set_tf_artikel.getText();
        String auftragsnummer = set_tf_auftrag.getText();
        if(artikelnummer.isEmpty() || auftragsnummer.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM Cabinet Where ID= 0 and Artikelnummer = ? and Auftragsnummer= ?";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1,artikelnummer );
                preparedStatement.setString(2,auftragsnummer);
                resultSet = (ResultSet) preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct artikelnummer/auftragsnummer");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Weiter Successful..Redirecting..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
        
        return status;
    }

private void setLblError(Color color, String text) {
        setLblError.setTextFill(color);
        setLblError.setText(text);
        System.out.println(text);
    }
  

  
    
  
}
