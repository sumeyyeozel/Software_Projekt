
// bu kisma klimaschrank ve databaase baglantisi gelecek
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import software_projekt.Database.DB_Connection;

/**
 *
 * @author sumey
 */
public class Software_Projekt extends Application {

   
    
    private double xOffset = 0;
	private double yOffset = 0;
	public static Hashtable<String, String> users = new Hashtable<>();
	public static PrintStream toCabinet;
        public static PrintStream toServer;
	public static BufferedReader fromCabinet;
	public static Socket socket;
        Connection con;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        public static BufferedReader fromServer;
        public static float Target=0;
        static String TargetTemp;
         private static float actualTemp = Float.MIN_VALUE;
           static boolean targetTempSet = false;
        private static  boolean targetTempReached = false;
        private static  boolean first =true;
        static float firstTargetTemp = 0f;
        static float secondTargetTemp = -25.8f;
        public static String answer;
        public static boolean TempControl = false;
        public static String opertemp;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/software_projekt/fxml/FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        connect2Cabinet(args);
       
        //connect2Database();
          launch(args);
		
    }
    
     public Software_Projekt() throws ClassNotFoundException{
        
             con = DB_Connection.conDB();
        
    }
        
            public static void connect2Cabinet(String[] args) {
				if (args.length == 0) {
			System.out.println("Usage: CabinetControl <port>");
			System.exit(0);
		}

	
			try {
			int port = Integer.parseInt(args[0]);
              
			socket = new Socket("localhost", port);
			Software_Projekt.toCabinet = new PrintStream(socket.getOutputStream(), true);
			Software_Projekt.fromCabinet = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    
    
    	public static void connect2Database() {
		
			Connection conn = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://sql11.freesqldatabase.com:3306/sql11412729?user=sql11412729&&password=r1pEpnJnYx");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
               

		String query = "SELECT * FROM Kunden";
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String userName = rs.getString("Name");
				String userRole = rs.getString("Rolle");
				String userStat = rs.getString("Status");
				if (userStat.equalsIgnoreCase("active")) {
					Software_Projekt.users.put(userName, userRole);
				}
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
        
       public static void test (Socket socket, PrintStream toServer,Vector<String> messages) throws IOException, InterruptedException {
                 fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
                 toServer= new PrintStream(socket.getOutputStream(), true);
          for(String message : messages) {
              System.out.println("=====>"+message);
                    toServer.println(message);
                     answer = fromServer.readLine();
                    System.out.println("<<==="+answer);
                    if(answer.startsWith("SETTARGET")){
                        actualTemp = Float.parseFloat(answer.substring(answer.indexOf(":")+1));
                        outerloop:
                         for(int i =0;i<message.length();i++){
                             if(message.charAt(i)=='|'){
                                 for(int j=i+1;j<message.length();j++)
                                     if(message.charAt(j)=='|'){
                                        TargetTemp= message.substring(i+1,j);
                                        System.out.println("Target Temperatur====" +TargetTemp);
                                        Target = Float.parseFloat(TargetTemp);
                                        break outerloop;
                                     }
                             }
                         }
                           while(!TempControl){
                           opertemp= "OPERTEMP";
                           Thread.sleep(1);
                           toServer.println(opertemp);
                           answer=fromServer.readLine();
                           System.out.println("<<==="+answer);
                           actualTemp = Float.parseFloat(answer.substring(answer.indexOf(":")+1));
                               System.out.println(+actualTemp+"=actualtemp");
                                
                                if(actualTemp >=0){
                                     if(actualTemp >=(Target-Target*0.1)&&actualTemp <= (Target+Target*0.1)){
                                 
                                            TempControl = true;}
                                
                          
                                 }else{     
                                     if(actualTemp <=(Target-Target*0.1)&&actualTemp >= (Target+Target*0.1)){
                                            TempControl = true;
                                    
                                        }
                                } 
                        
                            }
                           
                           TempControl = false;
                           System.out.println("Target Temperature Reached");
                     
               
                    
                    }
                
            }

          
             
                        }
                    
                    
                
            
        
        
        
        }
        
        

        
    
