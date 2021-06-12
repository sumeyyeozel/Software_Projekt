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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import software_projekt.Database.DB_Connection;

/**
 *
 * @author sumey
 */
public class FXMLDocumentController implements Initializable {

    public static String setEmail() {
        return email; 
    }
   
     //Anmeldung
    @FXML
    private Button anmelden;
    @FXML 
    private TextField benutzererk;
    @FXML 
    private PasswordField pass;
    @FXML
    private Label setLblError;
    


   
    Connection con=null;
    ResultSet resultSet=null;
    PreparedStatement preparedStatement=null;
    static String email;
    
    
  
    
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {
         if (event.getSource() == anmelden) {
            //login here
            if (anmelden().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/software_projekt/fxml/hauptmenu.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }
    
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
     public FXMLDocumentController() throws ClassNotFoundException{
        
             con = DB_Connection.conDB();
        
    }
     private String anmelden() {
        String status = "Success";
        String email = benutzererk.getText();
        String password = pass.getText();
        if(email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM Kunden Where email = ? and password = ?";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = (ResultSet) preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
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
