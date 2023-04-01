package com.sinkingships;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@RequiredArgsConstructor
public class AppGlobal {
    public static String userName;

    public static void loadNewScreen(String fxmlName, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlName));
        Scene scene = new Scene(fxmlLoader.load(), 1630, 1000);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getWindow().centerOnScreen();
    }
}
