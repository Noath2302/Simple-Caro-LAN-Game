package PocketWatch;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class AlertBox {
    static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Scene scene;
        Button button = new Button("Ok");
        Label label = new Label(message);
        button.setOnAction(e-> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(button,label);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout,140,140);
        scene.getStylesheets().add(Main.Player.currentTheme);
        window.setScene(scene);
        window.showAndWait();

    }
}
