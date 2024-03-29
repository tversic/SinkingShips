package com.sinkingships.controllers;

import com.sinkingships.Client;
import com.sinkingships.AppGlobal;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.Socket;

@NoArgsConstructor
public class ChoosePlayersScreenController {

    @FXML
    TextField enterPlayerNameField;
    @FXML
    ImageView boatImage;
    @FXML
    ImageView sailorOne;
    @FXML
    ImageView sailorTwo;
    @FXML
    AnchorPane anchorPaneChoosePlayer;
    @FXML
    AnchorPane rootPane;
    @FXML
    void funAnimation(){
        boatImage.setTranslateX(60);
        sailorOne.setRotate(180);
        sailorTwo.setRotate(180);
    }
    @FXML
    void funAnimationRelease(){
        boatImage.setTranslateX(-60);
        sailorOne.setRotate(360);
        sailorTwo.setRotate(360);
    }

    @FXML
    void startSailingButtonClicked() throws IOException {
        AppGlobal.userName = enterPlayerNameField.getText();
        System.out.println("WHOAMI " + AppGlobal.userName);
        AppGlobal.loadNewScreen("gameScreen.fxml", anchorPaneChoosePlayer);
    }
}
