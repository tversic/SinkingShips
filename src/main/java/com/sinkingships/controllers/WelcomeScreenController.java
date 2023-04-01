package com.sinkingships.controllers;

import com.sinkingships.AppGlobal;
import com.sinkingships.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class WelcomeScreenController {

    @FXML
    private AnchorPane ap;
    @FXML
    void startButtonClicked() throws IOException {
        System.out.println("Start button clicked");
        AppGlobal.loadNewScreen("choosePlayersScreen.fxml", ap);
    }
}
