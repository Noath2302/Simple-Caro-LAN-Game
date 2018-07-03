package PocketWatch;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * PLAYER INFO CLASS
 */
public class PlayerInfo {
    /**
     * BASIC INFORMATION
     */
    //adding player name
    String playerName,opponentName,clientName,hostName;;
    String playerRole;
    boolean myTurn;


    /**
     * NETWORKING PROPERTY
     */
    //adding connection status
    boolean connected;

    //adding MSG Property
    public StringProperty msgReceive;
    public StringProperty msgToSend;

    //adding port number
    int portNumber;

    //connection Thread
    Thread socketConnection;


    /**
     * GRAPHICS PROPERTY
     */
    //Javafx Window
    Stage window;
    Scene appScene;
    //theme
    /**
     * this value should only be input
     * as specified String for the theme
     * listed in the source code
     * further development may include
     * parsing a list of css files name to an ArrayList<String>
     */
    String currentTheme;

    //SymbolID
    int symbolID;

    /**
     * FUNCTIONS
     */
    //Constructor
    PlayerInfo(){
        //adding player's names
        playerName = "";opponentName = "";clientName = "";hostName = "";;
        playerRole = "";
        myTurn = true;
        //adding port number
        portNumber = 0;

        //adding connection status
        connected = false;
        //adding MSG Property
        msgReceive = new SimpleStringProperty(this,"","");
        msgToSend  = new SimpleStringProperty(this,"","");

        //theme initialization
        currentTheme = "PocketWatch/P1_D0.css";
    }
    public void PlayerReset(){
        //adding player's names
        playerName = "";opponentName = "";clientName = "";hostName = "";;
        playerRole = "";
        myTurn = true;
        //adding port number
        portNumber = 0;

        //adding connection status
        connected = false;

        //theme initialization
        currentTheme = "PocketWatch/P1_D0.css";
        msgToSend.set("");
        msgReceive.set("");
    }
    public void displayInfo(){
        System.out.println("playerName :" + playerName+" | opponentName :"+ opponentName);
        System.out.println("portNumber :" + portNumber+" | playerRole :"+playerRole);
        System.out.println("clientName :" + clientName+" | hostName :"+hostName);
        System.out.println("msgToSend :"+msgToSend+" | msgReceive :"+msgReceive);
        System.out.println("currentTheme :"+currentTheme);
    }

    /**
     * NETWORK FUNCTIONS
     */
    public void clientConnect(){
        try {
            socketConnection = new Thread(new ClientConnection(hostName,portNumber));
            connected = true;
        } catch (IOException e) {
            System.out.println("Can't connect to server");
            e.printStackTrace();
            connected = false;
        }
        socketConnection.start();
    }
    public void serverStart(){
        try {
            socketConnection = new Thread(new ServerConnection(portNumber));
            connected = true;
        } catch (IOException e) {
            System.out.println("Can't connect to server");
            e.printStackTrace();
            connected = false;
        }
        socketConnection.start();
    }
    /**
     * GUI FUNCTIONS
     * @param scene
     * @param title
     */
    public void changeScene(String scene,String title){
        Parent root = null;
        try {
           root = FXMLLoader.load(getClass().getResource(scene));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load scene");
        }
        window.setTitle(title);
        try{
            appScene = new Scene(root);
        }
        catch (NullPointerException e) {
            System.out.println("Could not load layout");
        }
        changeTheme();
        window.setScene(appScene);
        window.show();
    }
    public void changeTheme(){
        appScene.getStylesheets().removeAll("PocketWatch/P1_D0.css","PocketWatch/P1_D1.css","PocketWatch/P1_D2.css");
        appScene.getStylesheets().add(currentTheme);
    }
}

/**
 * DECLARING THREAD
 */
//CLIENT THREAD
class ClientConnection implements Runnable {
    Socket Cclient;
    Thread Csend, Creceive;

    ClientConnection(String CserverName, int CserverPort) throws IOException {
        Cclient = new Socket(CserverName, CserverPort);
        Csend = new Thread(new CclientSend(Cclient));
        Creceive = new Thread(new CclientReceive(Cclient));
    }

    public void run() {
        Csend.start();
        Creceive.start();
        if(!Creceive.isAlive()){
            try {
                Cclient.close();
                Main.Player.connected = false;
            } catch (IOException e) {
                System.out.println("Cclient has already closed");
                e.printStackTrace();
            }
        }
    }
}
class CclientReceive implements Runnable {
    private String Cclient_receive = "";
    DataInputStream CclientDIn;

    CclientReceive(Socket Cclient) throws IOException {
        CclientDIn = new DataInputStream(Cclient.getInputStream());
    }
    @Override
    public void run() {
        while (!Cclient_receive.equals("STOP")) {
            try {
                Cclient_receive = CclientDIn.readUTF();
                System.out.println("receive " + Cclient_receive);
                Main.Player.msgReceive.set(Cclient_receive);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Client exit Receive Stream ...");
            }
        }
    }
}
class CclientSend implements Runnable {
    private String Cclient_send = "";
    DataOutputStream CclientDOut;
    CclientSend(Socket Cclient) throws IOException {
        CclientDOut = new DataOutputStream(Cclient.getOutputStream());
        Main.Player.msgToSend.addListener((v,oldVal,newVal)->{
            try {
                Cclient_send = Main.Player.msgToSend.get();
                CclientDOut.writeUTF(Cclient_send);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run() {
        while (!Cclient_send.equals("STOP")) {
            Cclient_send = Main.Player.msgToSend.get();
        }
        System.out.println("Client exit Send Stream");
    }
}
//SERVER THREAD
class ServerConnection implements Runnable{
    ServerSocket Cserver;
    Socket Cclient;
    Thread Csend,Creceive;
    ServerConnection(int CserverPort) throws IOException {
        Cserver = new ServerSocket(CserverPort);
        Cclient = Cserver.accept();
        Csend = new Thread(new CserverSend(Cclient));
        Creceive = new Thread(new CserverReceive(Cclient));
    }
    public void run(){
        Csend.start();
        Creceive.start();
        if(!Csend.isAlive()){
            try {
                Cclient.close();
                Main.Player.connected = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Cserver.close();
                Main.Player.connected = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class CserverReceive implements Runnable{
    private String Cserver_receive = "";
    DataInputStream CserverDIn;
    CserverReceive(Socket Cclient) throws IOException {
        CserverDIn = new DataInputStream(Cclient.getInputStream());
    }
    @Override
    public void run() {
        while(!Cserver_receive.equals("STOP")){
            try {
                Cserver_receive = CserverDIn.readUTF();
                System.out.println("receive "+Cserver_receive);
                Main.Player.msgReceive.set(Cserver_receive);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Server exit Receive Stream ...");
            }
        }
        System.out.println("Server exit Receive Stream ...");
    }
}
class CserverSend implements Runnable{
    private String Cserver_send = "";
    DataOutputStream CserverDOut;
    CserverSend(Socket Cclient) throws IOException {
        CserverDOut = new DataOutputStream(Cclient.getOutputStream());
        Main.Player.msgToSend.addListener((v,oldVal,newVal)->{
            try {
                Cserver_send = Main.Player.msgToSend.get();
                CserverDOut.writeUTF(Cserver_send);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Override
    public void run() {
        while(!Cserver_send.equals("STOP")){
            Cserver_send = Main.Player.msgToSend.get();
        }
        System.out.println("Server exit Send Stream");
    }
}