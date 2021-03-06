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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import software_projekt.Database.DB_Connection;




import software_projekt.ModelTable;

/**
 * FXML Controller class
 *
 * @author beyzaesra und sumozel xd
 */
public class Check_in1Controller implements Initializable {


    @FXML
    private ImageView button_home;

    @FXML
    private ImageView button_abbrechen;

    @FXML
    private TableView<ModelTable> t_check_in1;

    @FXML
    private TableColumn<ModelTable, String> col_username;

    @FXML
    private TableColumn<ModelTable, String> col_bauteil_id;
    @FXML
    private TableColumn<ModelTable, Integer> col_id;
     @FXML
    private TableColumn<ModelTable, String> col_e_mail;


    @FXML
    private Button button_fertig;
    
    ObservableList<ModelTable> listM;
    

    
    
    int index = -1;
    //String query=null;
    Connection conn = null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    ModelTable modeltable = null;
    
    ObservableList<ModelTable> tablelist = FXCollections.observableArrayList();
    

      @Override
    public void initialize(URL url, ResourceBundle rb) {
       
            loadDate();
        
             listM= DB_Connection.getDataUsers();
             t_check_in1.setItems(listM);
        
    }
/*projeye uygun de??ildi. insert k??sm?? icindi yine.2
    @FXML
   private void enter_hinzufugen(MouseEvent event) {
    
       
        try {
            conn = DB_Connection.conDB();
            
            
             String id =slot_id.getText();
             String user =col_username.getText();
             String mail =col_e_mail.getText();
             String b_id =col_bauteil_id.getText();
            
            if(user.isEmpty() || mail.isEmpty() || b_id.isEmpty()){
                Alert alert = new Alert (Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Bosch b??rakma bitte");
                alert.showAndWait();
            }else{
                insert();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Check_in1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    
 
   }*/
    private void loadDate() {
       
        try {
            conn=DB_Connection.conDB();
            
            
            col_id.setCellValueFactory(new PropertyValueFactory<ModelTable, Integer>("id"));
            col_username.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("username"));
            col_e_mail.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("e_mail"));
            col_bauteil_id.setCellValueFactory(new PropertyValueFactory<ModelTable,String>("bauteil_id"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Check_in1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }


/*bizim projeye uygun de??ildi ui'den sildim burda b??yle kals??n. 
    private void insert(){
       
            try{
                
                conn=DB_Connection.conDB();
                
                
                String query = "insert into `ModelTable`(`id`, `username`, `e_mail`, `bauteil_id`) values (?,?,?,?)";
                try{
                    pst = conn.prepareStatement(query);
                    pst.setString(1, slot_id.getText());
                    pst.setString(2, username.getText());
                    pst.setString(3, e_mail.getText());
                    pst.setString(4, bauteil_id.getText());
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Data add succes");
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                
                
                
                
            }
            catch(ClassNotFoundException ex){
              Logger.getLogger(Check_in1Controller.class.getName()).log(Level.SEVERE,null, ex);
            }
     
    }*/
   @FXML
    private void test_begin(MouseEvent event) throws IOException {
         if (event.getSource() == button_fertig) {
            //login here
            //if (init_weiter().equals("Success")) {
              //  try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/software_projekt/fxml/FunktionsCheck1.fxml")));
                    stage.setScene(scene);
                    stage.show();

               // } catch (IOException ex) {
                 //   System.err.println(ex.getMessage());
                //}

            //}
    }}}
  
   



    
    
  