package PocketWatch;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class P2_C implements Initializable {
    //initialize image
    Image cell;
    Image[] symbol = new Image[2];
    //getting the scene pane
    @FXML
    Pane pane;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label roleLabel;
    Label playerTurnLabel = new Label();
    public StringProperty playerTurn = new SimpleStringProperty();
    @FXML
    public ImageView[][] imageBoard= new ImageView[16][16];
    public int[][] board = new int[16][16];

    @FXML
    public void concedeClicked(ActionEvent e){
        if(ConfirmBox.display("Concede","Are you sure?")) {
            Main.Player.msgToSend.set("CONCEDE");
        }
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        playerTurnLabel.textProperty().bind(playerTurn);
        playerTurnLabel.setLayoutX(70);
        playerTurnLabel.setLayoutY(155);
        anchorPane.getChildren().add(playerTurnLabel);
        //adding the symbol
        playerTurn.set(Main.Player.playerName);
        roleLabel.setText(Main.Player.playerRole);
        cell = new Image("PocketWatch/pic/cell.png");
        symbol[0] = new Image("PocketWatch/pic/cross.png");
        symbol[1] = new Image("PocketWatch/pic/circle.png");
        Main.Player.msgReceive.addListener((v,oldVal,newVal)->{
            receListen();
        });
        resetBoard();
        setGrid();
    }
    private boolean checkBoard(int x,int y){
        int yMin = (y-4<0)?0:y-4;
        int xMin = (x-4<0)?0:x-4;
        int xMax = (x+4>15)?11:x;
        int yMax = (y+4>15)?11:y;
        for(int i=xMin;i<=xMax;i+=1){
            if((board[i][y] == 1) && (board[i + 1][y] == 1) && (board[i + 2][y] == 1) && (board[i + 3][y] == 1) && (board[i + 4][y] == 1)){
                return true;
            }
        }
        for(int i=yMin;i<=yMax;i+=1){
            if((board[x][i] == 1) && (board[x][i + 1] == 1) && (board[x][i + 2] == 1) && (board[x][i + 3] == 1) && (board[x][i + 4] == 1)){
                return true;
            }
        }
        int[] dummyArray = new int[9];
        //top left to down right
        for(int i=-4;i<5;i+=1){
            dummyArray[i+4]=0;
            if((((x + i) < 16) && ((x + i) > -1)) && (((y + i) < 16) && ((y + i) > -1))) {
                dummyArray[i+4]=board[x+i][y+i];
            }
        }
        for(int i = 0;i<5;i+=1){
            if((dummyArray[i] == 1) && (dummyArray[i + 1] == 1) && (dummyArray[i + 2] == 1) && (dummyArray[i + 3] == 1) && (dummyArray[i + 4] == 1)){
                return true;
            }
        }
        for(int i=-4;i<5;i+=1){
            dummyArray[i+4]=0;
            if((((x + i) < 16) && ((x + i) > -1)) && (((y - i) < 16) && ((y - i) > -1))) {
                dummyArray[i+4]=board[x+i][y-i];
            }
        }
        for(int i = 0;i<5;i+=1){
            if((dummyArray[i] == 1) && (dummyArray[i + 1] == 1) && (dummyArray[i + 2] == 1) && (dummyArray[i + 3] == 1) && (dummyArray[i + 4] == 1)){
                return true;
            }
        }


        return false;
    } //under construction
    public void receListen(){
        String[] CinString = Main.Player.msgReceive.getValue().split("_");
        if(Main.Player.msgReceive.get().equals("STOP")){
            Main.Player.msgToSend.set("STOP");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AlertBox.display("The game has ended","back to Menu");
                    Main.Player.changeScene("P1_F.fxml","Hello again");
                }
            });
            return;
        }
        if(CinString[0].equals("CONCEDE")){
            Main.Player.msgToSend.set("WIN");
            return;
        }
        if(CinString[0].equals("NAME")){
            System.out.println(CinString[1]);
            Main.Player.opponentName = CinString[1];
            System.out.println(Main.Player.opponentName);
            return;
        }
        if(CinString[0].equals("WIN")){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AlertBox.display("GGWP","you lost");
                }
            });
            Main.Player.msgToSend.set("STOP");
            return;
        }
        if(CinString[0].equals("COORDINATE")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    playerTurn.set(Main.Player.playerName);
                }
            });
            int x = Integer.parseInt(CinString[1]);
            int y = Integer.parseInt(CinString[2]);
            imageBoard[x][y].setImage(symbol[(Main.Player.symbolID + 1) % 2]);
            imageBoard[x][y].setOnMouseClicked(null);
            Main.Player.myTurn = true;
            return;
        }
    }
    private void resetBoard(){
        for(int i = 0;i<16;i+=1){
            for(int j = 0;j<16;j+=1){
                board[i][j] = 0;
            }
        }
    }
    private void setGrid() {
        int x = 0,y = 0;
        while(x<16){
            while(y<16){
                imageBoard[x][y] = new ImageView(cell);
                imageBoard[x][y].setFitWidth(25);
                imageBoard[x][y].setFitHeight(25);
                imageBoard[x][y].setX(25*x);
                imageBoard[x][y].setY(25*y);
                int finalY = y;
                int finalX = x;
                imageBoard[x][y].setOnMouseClicked(e->{
                    if(Main.Player.myTurn) {
                        imageBoard[finalX][finalY].setImage(symbol[Main.Player.symbolID]);
                        board[finalX][finalY] = 1;
                        String coordinate = (String.format("COORDINATE_"+"%02d", finalX) + "_" + String.format("%02d", finalY));
                        System.out.println("send "+coordinate);
                        Main.Player.msgToSend.set(String.valueOf(coordinate));
                        imageBoard[finalX][finalY].setOnMouseClicked(null);
                        Main.Player.myTurn = false;
                        playerTurn.set(Main.Player.opponentName);
                        if(checkBoard(finalX,finalY)){
                            AlertBox.display("GGWP","You won the Game");
                            Main.Player.msgToSend.set("WIN");
                            if(ConfirmBox.display("You won","Play Again")){

                            }
                        }
                    }
                });
                pane.getChildren().add(imageBoard[x][y]);
                y+=1;
            }
            y=0;
            x+=1;
        }

    }
}
