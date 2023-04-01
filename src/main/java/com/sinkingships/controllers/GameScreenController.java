package com.sinkingships.controllers;

import com.sinkingships.gameObjects.Ship;
import com.sinkingships.utility.Draggable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@NoArgsConstructor
public class GameScreenController implements Initializable {

    private int cycleStatus = 0;
    private Draggable draggable = new Draggable();
    @FXML
    ImageView blackShip5;
    @FXML
    ImageView grayShip3;
    @FXML
    Pane pane00;
    @FXML
    Pane pane10;
    @FXML
    Pane pane20;
    @FXML
    Pane pane30;
    @FXML
    Pane pane40;
    @FXML
    Pane pane50;
    @FXML
    Pane pane60;
    @FXML
    Pane pane70;
    @FXML
    Pane pane01;
    @FXML
    Pane pane11;
    @FXML
    Pane pane21;
    @FXML
    Pane pane31;
    @FXML
    Pane pane41;
    @FXML
    Pane pane51;
    @FXML
    Pane pane61;
    @FXML
    Pane pane71;
    @FXML
    Pane pane02;
    @FXML
    Pane pane12;
    @FXML
    Pane pane22;
    @FXML
    Pane pane32;
    @FXML
    Pane pane42;
    @FXML
    Pane pane52;
    @FXML
    Pane pane62;
    @FXML
    Pane pane72;
    @FXML
    Pane pane03;
    @FXML
    Pane pane13;
    @FXML
    Pane pane23;
    @FXML
    Pane pane33;
    @FXML
    Pane pane43;
    @FXML
    Pane pane53;
    @FXML
    Pane pane63;
    @FXML
    Pane pane73;
    @FXML
    Pane pane04;
    @FXML
    Pane pane14;
    @FXML
    Pane pane24;
    @FXML
    Pane pane34;
    @FXML
    Pane pane44;
    @FXML
    Pane pane54;
    @FXML
    Pane pane64;
    @FXML
    Pane pane74;
    @FXML
    Pane pane05;
    @FXML
    Pane pane15;
    @FXML
    Pane pane25;
    @FXML
    Pane pane35;
    @FXML
    Pane pane45;
    @FXML
    Pane pane55;
    @FXML
    Pane pane65;
    @FXML
    Pane pane75;
    @FXML
    Pane pane06;
    @FXML
    Pane pane16;
    @FXML
    Pane pane26;
    @FXML
    Pane pane36;
    @FXML
    Pane pane46;
    @FXML
    Pane pane56;
    @FXML
    Pane pane66;
    @FXML
    Pane pane76;
    @FXML
    Pane pane07;
    @FXML
    Pane pane17;
    @FXML
    Pane pane27;
    @FXML
    Pane pane37;
    @FXML
    Pane pane47;
    @FXML
    Pane pane57;
    @FXML
    Pane pane67;
    @FXML
    Pane pane77;

    @FXML
    Pane paneOne;
    List<Ship> allShips = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggable.makeDraggable(blackShip5);
        draggable.makeDraggable(grayShip3);
        grayShip3.setVisible(false);
        allShips.add(new Ship(blackShip5, true));
        allShips.add(new Ship(grayShip3, false));
    }
    @FXML
    private void dragOverPane(){
        paneOne.setBackground(Background.fill(Paint.valueOf("yellow")));
    }

    @FXML
    private void setShip(){
        Ship activeShip = getActiveShip();
        System.out.println(activeShip.getShipImage().getId());
    }

    private Ship getActiveShip(){
        return allShips.stream().filter(Ship::isActive).findFirst().get();
    }

    @FXML
    private void rotateSip(){
        double rotate;
        Ship activeShip = allShips.stream().filter(Ship::isActive).findFirst().get();
        if(activeShip.getShipImage().getRotate() == 0)
            rotate = 90;
        else
            rotate = 0;
        activeShip.getShipImage().setRotate(rotate);
        activeShip.getShipImage().setLayoutX(180);
        activeShip.getShipImage().setLayoutY(250);

    }
}
