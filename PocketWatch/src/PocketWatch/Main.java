package PocketWatch;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * PlayerInfo appears in one program
     * as the parameter to pass to the
     * control class in the controllers
     */
    public static PlayerInfo Player = new PlayerInfo();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //get stage value for PlayerInfo
        Player.window = primaryStage;
        //going for first scene
        Player.changeScene("P1_F.fxml","Caro Game Welcome Scene");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
