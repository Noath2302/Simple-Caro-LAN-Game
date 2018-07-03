package PocketWatch;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static boolean answer;
    public static boolean display(String title,String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Button button1 = new Button("yes");
        Button button2 =new Button("no");

        button1.setOnAction(e->{
            answer = true;
            window.close();
        });
        button2.setOnAction(e->{
            answer = false;
            window.close();
        });

        Scene scene;
        Label label = new Label(message);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,button1,button2);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout,140,140);
        scene.getStylesheets().add(Main.Player.currentTheme);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
