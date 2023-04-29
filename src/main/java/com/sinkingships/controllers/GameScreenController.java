package com.sinkingships.controllers;

import com.sinkingships.AppGlobal;
import com.sinkingships.gameObjects.Ship;
import com.sinkingships.utility.Draggable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private String FIELD_TAKEN_ERROR_MESSAGE = "Ship cant be placed on chosen fields: Cause some of the fields are already taken, please choose another field";
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
    boolean[][] gameBoard = new boolean[8][8];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggable.makeDraggable(blackShip5);
        draggable.makeDraggable(grayShip3);
        grayShip3.setVisible(false);
        allShips.add(new Ship(blackShip5, true, "blackShip5"));
        allShips.add(new Ship(grayShip3, false, "grayShip3"));
    }
    @FXML
    private void dragOverPane(){
        paneOne.setBackground(Background.fill(Paint.valueOf("yellow")));
    }

    @FXML
    private void setShip(){
        findStartSquare();
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
    
    private void findStartSquare(){
        Ship activeShip = allShips.stream().filter(Ship::isActive).findFirst().get();
        double x = activeShip.getShipImage().getLayoutX();
        double y = activeShip.getShipImage().getLayoutY();
        boolean isRotate = activeShip.getShipImage().getRotate() != 0;
        int shipLength = Integer.parseInt(activeShip.getShipName().substring(activeShip.getShipName().length() - 1));
        int row = -1, column = -1;
        if(isRotate){
            // FIRST ROW
            if (x < -165 && y < 305) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 0;
            } else if (x < -55 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 1;
            } else if (x < 48 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 2;
            } else if (x < 150 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 3;
            } else if (x < 252 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 4;
            } else if (x < 354 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 5;
            } else if (x < 456 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 6;
            } else if (x < 558 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(200);
                row = 0;
                column = 7;
            }
            // SECOND ROW
            else if (x < -165 && y < 420) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 0;
            } else if (x < -55 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 1;
            } else if (x < 48 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 2;
            } else if (x < 150 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 3;
            } else if (x < 252 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 4;
            } else if (x < 354 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 5;
            } else if (x < 456 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 6;
            } else if (x < 558 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(307);
                row = 1;
                column = 7;
            }
            // THIRD ROW
            else if (x < -165 && y < 535) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 0;
            } else if (x < -55 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 1;
            } else if (x < 48 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 2;
            } else if (x < 150 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 3;
            } else if (x < 252 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 4;
            } else if (x < 354 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 5;
            } else if (x < 456 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 6;
            } else if (x < 558 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(422);
                row = 2;
                column = 7;
            }
            // FOURTH ROW
            else if (x < -165 && y < 650) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 0;
            } else if (x < -55 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 1;
            } else if (x < 48 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 2;
            } else if (x < 150 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 3;
            } else if (x < 252 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 4;
            } else if (x < 354 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 5;
            } else if (x < 456 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 6;
            } else if (x < 558 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(537);
                row = 3;
                column = 7;
            }
            // FIFTH ROW
            else if (x < -165 && y < 765) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 0;
            } else if (x < -55 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 1;
            } else if (x < 48 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 2;
            } else if (x < 150 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 3;
            } else if (x < 252 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 4;
            } else if (x < 354 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 5;
            } else if (x < 456 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 6;
            } else if (x < 558 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(652);
                row = 4;
                column = 7;
            }
            // SIXTH ROW
            else if (x < -165 && y < 880) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 0;
            } else if (x < -55 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 1;
            } else if (x < 48 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 2;
            } else if (x < 150 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 3;
            } else if (x < 252 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 4;
            } else if (x < 354 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 5;
            } else if (x < 456 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 6;
            } else if (x < 558 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(767);
                row = 5;
                column = 7;
            }
            // SEVENTH ROW
            else if (x < -165 && y < 995) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 0;
            } else if (x < -55 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 1;
            } else if (x < 48 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 2;
            } else if (x < 150 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 3;
            } else if (x < 252 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 4;
            } else if (x < 354 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 5;
            } else if (x < 456 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 6;
            } else if (x < 558 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(882);
                row = 6;
                column = 7;
            }
            // EIGHT ROW
            else if (x < -165 && y < 1105) {
                activeShip.getShipImage().setLayoutX(-210);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 0;
            } else if (x < -55 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(-110);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 1;
            } else if (x < 48 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(-5);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 2;
            } else if (x < 150 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(105);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 3;
            } else if (x < 252 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(205);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 4;
            } else if (x < 354 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(305);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 5;
            } else if (x < 456 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(405);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 6;
            } else if (x < 558 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(505);
                activeShip.getShipImage().setLayoutY(997);
                row = 7;
                column = 7;
            }

        } else {
            // FIRST COLUMN
            if(x < 90 && y < 65){
                activeShip.getShipImage().setLayoutX(0);
                activeShip.getShipImage().setLayoutY(25);
                column = 0;
                row = 0;
            } else if (x < 90 && y < 180) {
                activeShip.getShipImage().setLayoutX(0);
                activeShip.getShipImage().setLayoutY(125);
                column = 0;
                row = 1;
            } else if (x < 90 && y < 295) {
                activeShip.getShipImage().setLayoutX(0);
                activeShip.getShipImage().setLayoutY(233);
                column = 0;
                row = 2;
            } else if (x < 90 && y < 410) {
                activeShip.getShipImage().setLayoutX(0);
                activeShip.getShipImage().setLayoutY(352);
                column = 0;
                row = 3;
            } else if (x < 90 && y < 520) {
                activeShip.getShipImage().setLayoutX(0);
                activeShip.getShipImage().setLayoutY(465);
                column = 0;
                row = 4;
            } else if (x < 90 && y < 630) {
                activeShip.getShipImage().setLayoutX(0);
                activeShip.getShipImage().setLayoutY(575);
                column = 0;
                row = 5;
            } else if (x < 90 && y < 740) {
                activeShip.getShipImage().setLayoutX(0);
                activeShip.getShipImage().setLayoutY(685);
                column = 0;
                row = 6;
            }
            // SECOND COLUMN
            else if(x < 200 && y < 65){
                activeShip.getShipImage().setLayoutX(100);
                activeShip.getShipImage().setLayoutY(25);
                column = 1;
                row = 0;
            } else if (x < 200 && y < 180) {
                activeShip.getShipImage().setLayoutX(100);
                activeShip.getShipImage().setLayoutY(125);
                column = 1;
                row = 1;
            } else if (x < 200 && y < 295) {
                activeShip.getShipImage().setLayoutX(100);
                activeShip.getShipImage().setLayoutY(233);
                column = 1;
                row = 2;
            } else if (x < 200 && y < 410) {
                activeShip.getShipImage().setLayoutX(100);
                activeShip.getShipImage().setLayoutY(352);
                column = 1;
                row = 3;
            } else if (x < 200 && y < 520) {
                activeShip.getShipImage().setLayoutX(100);
                activeShip.getShipImage().setLayoutY(465);
                column = 1;
                row = 4;
            } else if (x < 200 && y < 630) {
                activeShip.getShipImage().setLayoutX(100);
                activeShip.getShipImage().setLayoutY(575);
                column = 1;
                row = 5;
            } else if (x < 200 && y < 740) {
                activeShip.getShipImage().setLayoutX(100);
                activeShip.getShipImage().setLayoutY(685);
                column = 1;
                row = 6;
            }
            //THIRD COLUMN
            else if(x < 300 && y < 65){
                activeShip.getShipImage().setLayoutX(150);
                activeShip.getShipImage().setLayoutY(25);
                column = 2;
                row = 0;
            } else if (x < 300 && y < 180) {
                activeShip.getShipImage().setLayoutX(200);
                activeShip.getShipImage().setLayoutY(125);
                column = 2;
                row = 1;
            } else if (x < 300 && y < 295) {
                activeShip.getShipImage().setLayoutX(200);
                activeShip.getShipImage().setLayoutY(233);
                column = 2;
                row = 2;
            } else if (x < 300 && y < 410) {
                activeShip.getShipImage().setLayoutX(200);
                activeShip.getShipImage().setLayoutY(352);
                column = 2;
                row = 3;
            } else if (x < 300 && y < 520) {
                activeShip.getShipImage().setLayoutX(200);
                activeShip.getShipImage().setLayoutY(465);
                column = 2;
                row = 4;
            } else if (x < 300 && y < 630) {
                activeShip.getShipImage().setLayoutX(200);
                activeShip.getShipImage().setLayoutY(575);
                column = 2;
                row = 5;
            } else if (x < 300 && y < 740) {
                activeShip.getShipImage().setLayoutX(200);
                activeShip.getShipImage().setLayoutY(685);
                column = 2;
                row = 6;
            }
            //FOURTH COLUMN
            else if(x < 400 && y < 65){
                activeShip.getShipImage().setLayoutX(300);
                activeShip.getShipImage().setLayoutY(25);
                column = 3;
                row = 0;
            } else if (x < 400 && y < 180) {
                activeShip.getShipImage().setLayoutX(300);
                activeShip.getShipImage().setLayoutY(125);
                column = 3;
                row = 1;
            } else if (x < 400 && y < 295) {
                activeShip.getShipImage().setLayoutX(300);
                activeShip.getShipImage().setLayoutY(233);
                column = 3;
                row = 2;
            } else if (x < 400 && y < 410) {
                activeShip.getShipImage().setLayoutX(300);
                activeShip.getShipImage().setLayoutY(352);
                column = 3;
                row = 3;
            } else if (x < 400 && y < 520) {
                activeShip.getShipImage().setLayoutX(300);
                activeShip.getShipImage().setLayoutY(465);
                column = 3;
                row = 4;
            } else if (x < 400 && y < 630) {
                activeShip.getShipImage().setLayoutX(300);
                activeShip.getShipImage().setLayoutY(575);
                column = 3;
                row = 5;
            } else if (x < 400 && y < 740) {
                activeShip.getShipImage().setLayoutX(300);
                activeShip.getShipImage().setLayoutY(685);
                column = 3;
                row = 6;
            }
            //FIFTH COLUMN
            else if(x < 500 && y < 65){
                activeShip.getShipImage().setLayoutX(400);
                activeShip.getShipImage().setLayoutY(25);
                column = 4;
                row = 0;
            } else if (x < 500 && y < 180) {
                activeShip.getShipImage().setLayoutX(400);
                activeShip.getShipImage().setLayoutY(125);
                column = 4;
                row = 1;
            } else if (x < 500 && y < 295) {
                activeShip.getShipImage().setLayoutX(400);
                activeShip.getShipImage().setLayoutY(233);
                column = 4;
                row = 2;
            } else if (x < 500 && y < 410) {
                activeShip.getShipImage().setLayoutX(400);
                activeShip.getShipImage().setLayoutY(352);
                column = 4;
                row = 3;
            } else if (x < 500 && y < 520) {
                activeShip.getShipImage().setLayoutX(400);
                activeShip.getShipImage().setLayoutY(465);
                column = 4;
                row = 4;
            } else if (x < 500 && y < 630) {
                activeShip.getShipImage().setLayoutX(400);
                activeShip.getShipImage().setLayoutY(575);
                column = 4;
                row = 5;
            } else if (x < 500 && y < 740) {
                activeShip.getShipImage().setLayoutX(400);
                activeShip.getShipImage().setLayoutY(685);
                column = 4;
                row = 6;
            }
            //SIXTH COLUMN
            else if(x < 600 && y < 65){
                activeShip.getShipImage().setLayoutX(500);
                activeShip.getShipImage().setLayoutY(25);
                column = 5;
                row = 0;
            } else if (x < 600 && y < 180) {
                activeShip.getShipImage().setLayoutX(500);
                activeShip.getShipImage().setLayoutY(125);
                column = 5;
                row = 1;
            } else if (x < 600 && y < 295) {
                activeShip.getShipImage().setLayoutX(500);
                activeShip.getShipImage().setLayoutY(233);
                column = 5;
                row = 2;
            } else if (x < 600 && y < 410) {
                activeShip.getShipImage().setLayoutX(500);
                activeShip.getShipImage().setLayoutY(352);
                column = 5;
                row = 3;
            } else if (x < 600 && y < 520) {
                activeShip.getShipImage().setLayoutX(500);
                activeShip.getShipImage().setLayoutY(465);
                column = 5;
                row = 4;
            } else if (x < 600 && y < 630) {
                activeShip.getShipImage().setLayoutX(500);
                activeShip.getShipImage().setLayoutY(575);
                column = 5;
                row = 5;
            } else if (x < 600 && y < 740) {
                activeShip.getShipImage().setLayoutX(500);
                activeShip.getShipImage().setLayoutY(685);
                column = 5;
                row = 6;
            }
            //SEVENTH COLUMN
            else if(x < 700 && y < 65){
                activeShip.getShipImage().setLayoutX(600);
                activeShip.getShipImage().setLayoutY(25);
                column = 6;
                row = 0;
            } else if (x < 700 && y < 180) {
                activeShip.getShipImage().setLayoutX(600);
                activeShip.getShipImage().setLayoutY(125);
                column = 6;
                row = 1;
            } else if (x < 700 && y < 295) {
                activeShip.getShipImage().setLayoutX(600);
                activeShip.getShipImage().setLayoutY(233);
                column = 6;
                row = 2;
            } else if (x < 700 && y < 410) {
                activeShip.getShipImage().setLayoutX(600);
                activeShip.getShipImage().setLayoutY(352);
                column = 6;
                row = 3;
            } else if (x < 700 && y < 520) {
                activeShip.getShipImage().setLayoutX(600);
                activeShip.getShipImage().setLayoutY(465);
                column = 6;
                row = 4;
            } else if (x < 700 && y < 630) {
                activeShip.getShipImage().setLayoutX(600);
                activeShip.getShipImage().setLayoutY(575);
                column = 6;
                row = 5;
            } else if (x < 700 && y < 740) {
                activeShip.getShipImage().setLayoutX(600);
                activeShip.getShipImage().setLayoutY(685);
                column = 6;
                row = 6;
            }
            //EIGTH COLUMN
            else if(x < 800 && y < 65){
                activeShip.getShipImage().setLayoutX(700);
                activeShip.getShipImage().setLayoutY(25);
                column = 7;
                row = 0;
            } else if (x < 800 && y < 180) {
                activeShip.getShipImage().setLayoutX(700);
                activeShip.getShipImage().setLayoutY(125);
                column = 7;
                row = 1;
            } else if (x < 800 && y < 295) {
                activeShip.getShipImage().setLayoutX(700);
                activeShip.getShipImage().setLayoutY(233);
                column = 7;
                row = 2;
            } else if (x < 800 && y < 410) {
                activeShip.getShipImage().setLayoutX(700);
                activeShip.getShipImage().setLayoutY(352);
                column = 7;
                row = 3;
            } else if (x < 800 && y < 520) {
                activeShip.getShipImage().setLayoutX(700);
                activeShip.getShipImage().setLayoutY(465);
                column = 7;
                row = 4;
            } else if (x < 800 && y < 630) {
                activeShip.getShipImage().setLayoutX(700);
                activeShip.getShipImage().setLayoutY(575);
                column = 7;
                row = 5;
            } else if (x < 800 && y < 740) {
                activeShip.getShipImage().setLayoutX(700);
                activeShip.getShipImage().setLayoutY(685);
                column = 7;
                row = 6;
            }
        }

        if(!overlapCheck(shipLength, isRotate, row, column)) {
            saveGameBoardState(shipLength, isRotate, row, column);
            System.out.println("Ship " + activeShip.getShipName() + " set to square " + row + ", " + column);
        }
        else
            AppGlobal.showErrorMessage(FIELD_TAKEN_ERROR_MESSAGE);

        System.out.println(x + " " + y);
    }

    private void saveGameBoardState(int shipLength, boolean isRotated, int column, int row) {
        if (isRotated)
            for (int i = 0; i < shipLength; i++) {
                if (gameBoard[column][i] == false)
                    gameBoard[column][i] = true;
                else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("THIS FIELD IS ALREADY TAKEN");
                    errorAlert.setContentText("PLEASE MOVE YOUR SHIP TO THE FIELDS THAT ARE NOT ALREADY TAKEN");
                    errorAlert.showAndWait();
                    break;
                }
            }
        else
            for (int i = 0; i < shipLength; i++) {
                if (gameBoard[column][i] == false)
                    gameBoard[i][row] = true;
                else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("THIS FIELD IS ALREADY TAKEN");
                    errorAlert.setContentText("PLEASE MOVE YOUR SHIP TO THE FIELDS THAT ARE NOT ALREADY TAKEN");
                    errorAlert.showAndWait();
                    break;
                }
            }
    }
    private boolean overlapCheck(int shipLength, boolean isRotated,  int row, int column) {

            for (int i = 0; i < shipLength; i++) {
                if (isRotated) {
                    if (gameBoard[column][row + i] == true)
                        return true;
                }
                else {
                    if (gameBoard[column + i][row] == true)
                        return true;
                }
            }
            return false;
    }

}
