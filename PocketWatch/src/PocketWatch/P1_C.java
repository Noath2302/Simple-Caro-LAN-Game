package PocketWatch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;


public class P1_C implements Initializable {
    @FXML
    Label host;
    @FXML
    Label playerRoleLabel;
    @FXML
    TextField hostField;
    @FXML
    TextField portNumberField;
    @FXML TextField playerNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hostField.setVisible(false);
        host.setVisible(false);
        Main.Player.PlayerReset();
    }

    public boolean check(){
        if(portNumberField.getText().equals("")
                || playerNameField.getText().equals("")
                || Main.Player.playerRole.equals("")){
            return false;
        }
        else return true;
    }
    //choosing role as client or player
    @FXML public void clientClicked(ActionEvent e){
        host.setVisible(false);
        hostField.setVisible(true);
        Main.Player.playerRole = "CLIENT";
        playerRoleLabel.setText("CLIENT");
        Main.Player.symbolID = 1;
    }
    @FXML public  void serverClicked(ActionEvent e){
        hostField.setVisible(false);
        host.setVisible(true);
        try {
            host.setText(String.valueOf(InetAddress.getLocalHost()));
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        Main.Player.playerRole = "SERVER";
        playerRoleLabel.setText("SERVER");
        Main.Player.symbolID = 0;
    }
    //the start button that check the information and proceed to connection
    @FXML public void startClicked(ActionEvent e){
        //getting information
        Main.Player.portNumber = (!portNumberField.getText().equals(""))?Integer.parseInt(portNumberField.getText()):0;
        Main.Player.playerName = playerNameField.getText();
        if(Main.Player.playerRole.equals("CLIENT")){
            Main.Player.hostName = hostField.getText();
            System.out.println("local host:"+Main.Player.hostName);
        }
        else if(Main.Player.playerRole.equals("SERVER")){
            try {
                Main.Player.hostName = InetAddress.getLocalHost().getHostName();
                System.out.println("local host:"+Main.Player.hostName);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
                Main.Player.hostName = "";
                System.out.println("no local host found");
            }
        }

        //check if the information is valid
        if(check()){
            //start the connection
            if(Main.Player.playerRole.equals("CLIENT")){
                Main.Player.clientConnect();
            }
            else if(Main.Player.playerRole.equals("SERVER")){
                Main.Player.serverStart();
            }
            //changing the Scene
            Main.Player.changeScene("P2_F.fxml","Caro Game Game Scene");
            Main.Player.msgToSend.set("NAME_"+Main.Player.playerName);
        }
        else {
            //start the AlertBox error prone
        }
    }
    @FXML public void exitClicked(ActionEvent e){
        //exit from application
        Platform.exit();
    }

    @FXML public void darkTheme(ActionEvent e){
        Main.Player.currentTheme = "PocketWatch/P1_D1.css";
        Main.Player.changeTheme();
    }
    @FXML public void lightTheme(ActionEvent e){
        Main.Player.currentTheme = "PocketWatch/P1_D0.css";
        Main.Player.changeTheme();
    }
    @FXML public void sepiaTheme(ActionEvent e){
        Main.Player.currentTheme = "PocketWatch/P1_D2.css";
        Main.Player.changeTheme();
    }
    @FXML public void  germaTheme(ActionEvent e){
        Main.Player.currentTheme = "PocketWatch/P1_D3.css";
        Main.Player.changeTheme();
    }
}
