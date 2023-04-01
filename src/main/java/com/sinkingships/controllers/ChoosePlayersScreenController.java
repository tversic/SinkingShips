package com.sinkingships.controllers;

import com.sinkingships.AppGlobal;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.NoArgsConstructor;

import java.io.IOException;

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
        AppGlobal.loadNewScreen("gameScreen.fxml", anchorPaneChoosePlayer);
        System.out.println(AppGlobal.userName);
    }
}
