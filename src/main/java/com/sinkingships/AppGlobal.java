package com.sinkingships;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@RequiredArgsConstructor
public class AppGlobal {
    public static String userName;

    public static boolean isGameStarted = false;
    public static void loadNewScreen(String fxmlName, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlName));
        Scene scene = new Scene(fxmlLoader.load(), 1620, 990);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        if (AppGlobal.userName != null)
         stage.setTitle("Player: " + AppGlobal.userName);
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getWindow().centerOnScreen();
    }

    public static void showErrorMessage(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("ERROR");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
    public static void showWinningMessage(String usernamePlayer) {
        Alert winningAlert = new Alert(Alert.AlertType.INFORMATION);
        winningAlert.setHeaderText("WIN");
        winningAlert.setContentText("Player " + usernamePlayer + " has won a game");
        winningAlert.showAndWait();
    }
}
