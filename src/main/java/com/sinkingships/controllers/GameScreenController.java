package com.sinkingships.controllers;

import com.sinkingships.AppGlobal;
import com.sinkingships.Client;
import com.sinkingships.gameObjects.Ship;
import com.sinkingships.utility.Draggable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@NoArgsConstructor
public class GameScreenController implements Initializable {
    private boolean win = false;

    private int cycleStatus = 0;
    private Draggable draggable = new Draggable();
    private String FIELD_TAKEN_ERROR_MESSAGE = "Ship cant be placed on chosen fields: Cause some of the fields are already taken, please choose another field";
    @FXML
    ImageView blackShip5;
    @FXML
    ImageView grayShip3;
    @FXML
    ImageView brownShip5;
    @FXML
    ImageView secondGrayShip3;
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
    Button startButton;
    @FXML
    Button readyButton;
    @FXML
    Button rotateShip;
    @FXML
    Button setShip;
    @FXML
    Label waitLable;
    @FXML
    Label yourTurnLabel;
    @FXML
    Label waitForPlayer;
    boolean myTurn = false;

    Pane clickedPane;

    List<Ship> allShips = new ArrayList<>();
    boolean[][] gameBoard = new boolean[8][8];
    boolean[][] opponentsBoard = new boolean[8][8];

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    Client client;
    Socket socket1 = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggable.makeDraggable(blackShip5);
        draggable.makeDraggable2(grayShip3);
        draggable.makeDraggable(brownShip5);
        draggable.makeDraggable2(secondGrayShip3);
        grayShip3.setVisible(false);
        brownShip5.setVisible(false);
        secondGrayShip3.setVisible(false);
        startButton.setVisible(false);
        readyButton.setVisible(false);
        waitLable.setVisible(false);
        waitForPlayer.setVisible(false);
        yourTurnLabel.setVisible(false);
        allShips.add(new Ship(blackShip5, true, "blackShip5"));
        allShips.add(new Ship(grayShip3, false, "grayShip3"));
        allShips.add(new Ship(brownShip5, false, "browShip5"));

        allShips.add(new Ship(secondGrayShip3, false, "secondGrayShip3"));


        //SOCKET INITIALIZATION
        try {
            socket1 = new Socket("localhost", 1234);


            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            client = new Client(socket1, AppGlobal.userName);
            this.listenForMessage();
            client.sendMessage2("test message");
            /*client.sendMessage();*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                String msgFromServer;

                while (socket1.isConnected()) {
                    try {
                        msgFromServer = bufferedReader.readLine();
                        if (msgFromServer.contains("BOTH_READY")) {
                            bothReady();
                        } else if (msgFromServer.contains("YOUR_TURN")) {
                            myTurn = true;
                            showMyTurnText();
                        } else if (msgFromServer.contains(("OPPONENTS_TURN"))) {
                            myTurn = false;
                            showNotMyTurnText();
                        } else if (msgFromServer.contains("HIT")) {
                            if (clickedPane != null)
                                clickedPane.setBackground(Background.fill(Paint.valueOf("red")));
                        } else if (msgFromServer.contains("WIN")) {
                            String finalMsgFromServer = msgFromServer;
                            win = true;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    AppGlobal.showWinningMessage(finalMsgFromServer);
                                }
                            });
                        } else if (msgFromServer.contains("FINISH")) {
                            if (!win)
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppGlobal.showWinningMessage("YOUR OPPONENT HAS WON THE GAME!");
                                    }
                                });
                            Thread.sleep(5000);
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        closeEverything(socket1, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    private void showMyTurnText() {
        yourTurnLabel.setVisible(true);
        waitForPlayer.setVisible(false);
    }

    private void showNotMyTurnText() {
        yourTurnLabel.setVisible(false);
        waitForPlayer.setVisible(true);
    }

    @FXML
    private void dragOverPane(){
       // paneOne.setBackground(Background.fill(Paint.valueOf("yellow")));
    }

    @FXML
    private void setShip(){
        if (findStartSquare()) {
            if (blackShip5.isVisible() && !grayShip3.isVisible()
                    && !brownShip5.isVisible() && !secondGrayShip3.isVisible()) {
                draggable.makeNotDraggable(blackShip5);
                grayShip3.setVisible(true);
                Ship activeShip = allShips.stream().filter(s -> s.getShipImage() == blackShip5).findFirst().get();
                activeShip.setActive(false);
                activeShip = allShips.stream().filter(s -> s.getShipImage() == grayShip3).findFirst().get();
                activeShip.setActive(true);
            } else if (blackShip5.isVisible() && grayShip3.isVisible()
                    && !brownShip5.isVisible() && !secondGrayShip3.isVisible()) {
                brownShip5.setVisible(true);
                draggable.makeNotDraggable(grayShip3);
                Ship activeShip = allShips.stream().filter(s -> s.getShipImage() == grayShip3).findFirst().get();
                activeShip.setActive(false);
                activeShip = allShips.stream().filter(s -> s.getShipImage() == brownShip5).findFirst().get();
                activeShip.setActive(true);
            } else if (blackShip5.isVisible() && grayShip3.isVisible()
                    && brownShip5.isVisible() && !secondGrayShip3.isVisible()) {
                secondGrayShip3.setVisible(true);
                draggable.makeNotDraggable(brownShip5);
                Ship activeShip = allShips.stream().filter(s -> s.getShipImage() == brownShip5).findFirst().get();
                activeShip.setActive(false);
                activeShip = allShips.stream().filter(s -> s.getShipImage() == secondGrayShip3).findFirst().get();
                activeShip.setActive(true);
            } else if (blackShip5.isVisible() && grayShip3.isVisible()
                    && brownShip5.isVisible() && secondGrayShip3.isVisible()) {
                draggable.makeNotDraggable(secondGrayShip3);
                Ship activeShip = allShips.stream().filter(s -> s.getShipImage() == brownShip5).findFirst().get();
                activeShip.setActive(false);
                readyButton.setVisible(true);
                setShip.setVisible(false);
                rotateShip.setVisible(false);
            }
        }
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
    
    private boolean findStartSquare(){
        Ship activeShip = allShips.stream().filter(Ship::isActive).findFirst().get();
        double x = activeShip.getShipImage().getLayoutX();
        double y = activeShip.getShipImage().getLayoutY();
        double grayShipXoffset = 0;
        double grayShipYoffset = 0;

        boolean isRotate = activeShip.getShipImage().getRotate() != 0;
        if ((((activeShip.getShipName().equals("grayShip3") || activeShip.getShipName().equals("secondGrayShip3")) && isRotate))) {
            grayShipXoffset = 120;
            grayShipYoffset = 100;
            x -= grayShipXoffset;
            y += grayShipYoffset;
        }
        int shipLength = Integer.parseInt(activeShip.getShipName().substring(activeShip.getShipName().length() - 1));
        int row = -1, column = -1;
        if(isRotate) {
            // FIRST ROW
            if (x < -165 && y < 305) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 0;
            } else if (x < -55 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 1;
            } else if (x < 48 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 2;
            } else if (x < 150 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 3;
            } else if (x < 252 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 4;
            } else if (x < 354 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 5;
            } else if (x < 456 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 6;
            } else if (x < 558 &&  y < 305) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(200 - grayShipYoffset);
                row = 0;
                column = 7;
            }
            // SECOND ROW
            else if (x < -165 && y < 420) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 0;
            } else if (x < -55 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 1;
            } else if (x < 48 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 2;
            } else if (x < 150 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 3;
            } else if (x < 252 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 4;
            } else if (x < 354 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 5;
            } else if (x < 456 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 6;
            } else if (x < 558 &&  y < 420) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(307 - grayShipYoffset);
                row = 1;
                column = 7;
            }
            // THIRD ROW
            else if (x < -165 && y < 535) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 0;
            } else if (x < -55 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 1;
            } else if (x < 48 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 2;
            } else if (x < 150 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 3;
            } else if (x < 252 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 4;
            } else if (x < 354 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 5;
            } else if (x < 456 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 6;
            } else if (x < 558 &&  y < 535) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(422 - grayShipYoffset);
                row = 2;
                column = 7;
            }
            // FOURTH ROW
            else if (x < -165 && y < 650) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 0;
            } else if (x < -55 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 1;
            } else if (x < 48 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 2;
            } else if (x < 150 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 3;
            } else if (x < 252 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 4;
            } else if (x < 354 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 5;
            } else if (x < 456 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 6;
            } else if (x < 558 &&  y < 650) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(537 - grayShipYoffset);
                row = 3;
                column = 7;
            }
            // FIFTH ROW
            else if (x < -165 && y < 765) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 0;
            } else if (x < -55 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 1;
            } else if (x < 48 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 2;
            } else if (x < 150 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 3;
            } else if (x < 252 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 4;
            } else if (x < 354 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 5;
            } else if (x < 456 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 6;
            } else if (x < 558 &&  y < 765) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(652 - grayShipYoffset);
                row = 4;
                column = 7;
            }
            // SIXTH ROW
            else if (x < -165 && y < 880) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 0;
            } else if (x < -55 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 1;
            } else if (x < 48 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 2;
            } else if (x < 150 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 3;
            } else if (x < 252 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 4;
            } else if (x < 354 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 5;
            } else if (x < 456 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 6;
            } else if (x < 558 &&  y < 880) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(767 - grayShipYoffset);
                row = 5;
                column = 7;
            }
            // SEVENTH ROW
            else if (x < -165 && y < 995) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 0;
            } else if (x < -55 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 1;
            } else if (x < 48 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 2;
            } else if (x < 150 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 3;
            } else if (x < 252 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 4;
            } else if (x < 354 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 5;
            } else if (x < 456 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 6;
            } else if (x < 558 &&  y < 995) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(882 - grayShipYoffset);
                row = 6;
                column = 7;
            }
            // EIGHT ROW
            else if (x < -165 && y < 1105) {
                activeShip.getShipImage().setLayoutX(-210 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 0;
            } else if (x < -55 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(-110 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 1;
            } else if (x < 48 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(-5 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 2;
            } else if (x < 150 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(105 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 3;
            } else if (x < 252 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(205 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 4;
            } else if (x < 354 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(305 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 5;
            } else if (x < 456 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(405 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 6;
            } else if (x < 558 &&  y < 1105) {
                activeShip.getShipImage().setLayoutX(505 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(997 - grayShipYoffset);
                row = 7;
                column = 7;
            }

        } else {
            // FIRST COLUMN
            if(x < 90 && y < 65){
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 0;
                row = 0;
            } else if (x < 90 && y < 180) {
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 0;
                row = 1;
            } else if (x < 90 && y < 295) {
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 0;
                row = 2;
            } else if (x < 90 && y < 410) {
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 0;
                row = 3;
            } else if (x < 90 && y < 520) {
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 0;
                row = 4;
            } else if (x < 90 && y < 630) {
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 0;
                row = 5;
            } else if (x < 90 && y < 740) {
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(690 - grayShipYoffset);
                column = 0;
                row = 6;
            }
            else if (x < 90 && y > 740) {
                activeShip.getShipImage().setLayoutX(0 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(800 - grayShipYoffset);
                column = 0;
                row = 7;
            }
            // SECOND COLUMN
            else if(x < 200 && y < 65){
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 1;
                row = 0;
            } else if (x < 200 && y < 180) {
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 1;
                row = 1;
            } else if (x < 200 && y < 295) {
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 1;
                row = 2;
            } else if (x < 200 && y < 410) {
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 1;
                row = 3;
            } else if (x < 200 && y < 520) {
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 1;
                row = 4;
            } else if (x < 200 && y < 630) {
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 1;
                row = 5;
            } else if (x < 200 && y < 740) {
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(690 - grayShipYoffset);
                column = 1;
                row = 6;
            }
            else if (x < 200 && y >= 740) {
                activeShip.getShipImage().setLayoutX(100 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(800 - grayShipYoffset);
                column = 1;
                row = 7;
            }
            //THIRD COLUMN
            else if(x < 300 && y < 65){
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 2;
                row = 0;
            } else if (x < 300 && y < 180) {
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 2;
                row = 1;
            } else if (x < 300 && y < 295) {
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 2;
                row = 2;
            } else if (x < 300 && y < 410) {
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 2;
                row = 3;
            } else if (x < 300 && y < 520) {
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 2;
                row = 4;
            } else if (x < 300 && y < 630) {
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 2;
                row = 5;
            } else if (x < 300 && y <= 740) {
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(690 - grayShipYoffset);
                column = 2;
                row = 6;
            }
            else if (x < 300 && y > 740) {
                activeShip.getShipImage().setLayoutX(200 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(800 - grayShipYoffset);
                column = 2;
                row = 7;
            }
            //FOURTH COLUMN
            else if(x < 400 && y < 65){
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 3;
                row = 0;
            } else if (x < 400 && y < 180) {
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 3;
                row = 1;
            } else if (x < 400 && y < 295) {
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 3;
                row = 2;
            } else if (x < 400 && y < 410) {
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 3;
                row = 3;
            } else if (x < 400 && y < 520) {
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 3;
                row = 4;
            } else if (x < 400 && y < 630) {
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 3;
                row = 5;
            }
            else if (x < 400 && y <= 740) {
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(685 - grayShipYoffset);
                column = 3;
                row = 6;
            } else if (x < 400 && y > 740) {
                activeShip.getShipImage().setLayoutX(300 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(800 - grayShipYoffset);
                column = 3;
                row = 7;
            }
            //FIFTH COLUMN
            else if(x < 500 && y < 65){
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 4;
                row = 0;
            } else if (x < 500 && y < 180) {
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 4;
                row = 1;
            } else if (x < 500 && y < 295) {
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 4;
                row = 2;
            } else if (x < 500 && y < 410) {
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 4;
                row = 3;
            } else if (x < 500 && y < 520) {
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 4;
                row = 4;
            } else if (x < 500 && y < 630) {
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 4;
                row = 5;
            } else if (x < 500 && y < 740) {
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(685 - grayShipYoffset);
                column = 4;
                row = 6;
            }
            else if (x < 500 && y > 740) {
                activeShip.getShipImage().setLayoutX(400 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(800 - grayShipYoffset);
                column = 4;
                row = 7;
            }
            //SIXTH COLUMN
            else if(x < 600 && y < 65){
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 5;
                row = 0;
            } else if (x < 600 && y < 180) {
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 5;
                row = 1;
            } else if (x < 600 && y < 295) {
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 5;
                row = 2;
            } else if (x < 600 && y < 410) {
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 5;
                row = 3;
            } else if (x < 600 && y < 520) {
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 5;
                row = 4;
            } else if (x < 600 && y < 630) {
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 5;
                row = 5;
            } else if (x < 600 && y <= 740) {
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(685 - grayShipYoffset);
                column = 5;
                row = 6;
            } else if (x < 600 && y > 740) {
                activeShip.getShipImage().setLayoutX(500 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(800 - grayShipYoffset);
                column = 5;
                row = 7;
            }
            //SEVENTH COLUMN
            else if(x < 700 && y < 65){
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 6;
                row = 0;
            } else if (x < 700 && y < 180) {
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 6;
                row = 1;
            } else if (x < 700 && y < 295) {
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 6;
                row = 2;
            } else if (x < 700 && y < 410) {
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 6;
                row = 3;
            } else if (x < 700 && y < 520) {
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 6;
                row = 4;
            } else if (x < 700 && y < 630) {
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 6;
                row = 5;
            } else if (x < 700 && y < 740) {
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(685 - grayShipYoffset);
                column = 6;
                row = 6;
            }  else if (x < 700 && y > 740) {
                activeShip.getShipImage().setLayoutX(600 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(800 - grayShipYoffset);
                column = 6;
                row = 7;
            }
            //EIGHTH COLUMN
            else if(x < 800 && y < 65){
                activeShip.getShipImage().setLayoutX(700 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(25 - grayShipYoffset);
                column = 7;
                row = 0;
            } else if (x < 800 && y < 180) {
                activeShip.getShipImage().setLayoutX(700 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(125 - grayShipYoffset);
                column = 7;
                row = 1;
            } else if (x < 800 && y < 295) {
                activeShip.getShipImage().setLayoutX(700 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(233 - grayShipYoffset);
                column = 7;
                row = 2;
            } else if (x < 800 && y < 410) {
                activeShip.getShipImage().setLayoutX(700 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(352 - grayShipYoffset);
                column = 7;
                row = 3;
            } else if (x < 800 && y < 520) {
                activeShip.getShipImage().setLayoutX(700 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(465 - grayShipYoffset);
                column = 7;
                row = 4;
            } else if (x < 800 && y < 630) {
                activeShip.getShipImage().setLayoutX(700 + grayShipXoffset);
                activeShip.getShipImage().setLayoutY(575 - grayShipYoffset);
                column = 7;
                row = 5;
            }
        }

        System.out.println(x + " " + y);
        if(!overlapCheck(shipLength, isRotate, row, column)) {
            saveGameBoardState(shipLength, isRotate, column, row);
            System.out.println("Ship " + activeShip.getShipName() + " set to square -> row: " + row + ", column: " + column);
            return true;
        }
        else {
            AppGlobal.showErrorMessage(FIELD_TAKEN_ERROR_MESSAGE);
            return false;
        }
    }

    private void saveGameBoardState(int shipLength, boolean isRotated, int column, int row) {
        if (isRotated)
            for (int i = 0; i < shipLength; i++) {
                    gameBoard[column][row + i] = true;
            }
        else
            for (int i = 0; i < shipLength; i++) {
                    gameBoard[column + i][row] = true;
            }
    }
    private boolean overlapCheck(int shipLength, boolean isRotated,  int row, int column) {

            for (int i = 0; i < shipLength; i++) {
                if (isRotated) {
                    if (gameBoard[column][row + i])
                        return true;
                }
                else {
                    if (gameBoard[column + i][row])
                        return true;
                }
            }
            return false;
    }
    @FXML
    private void startGame(){
        this.opponentsBoard = this.gameBoard;
        client.sendMessage2( "START " + " >" + Arrays.deepToString(this.gameBoard));
        System.out.println("start game");
        AppGlobal.isGameStarted = true;
    }
    @FXML
    private void hit(MouseEvent event) {
        if (AppGlobal.isGameStarted && myTurn) {
            clickedPane = (Pane) event.getSource();
            System.out.println("Clicked pane: " + clickedPane.getId());
            didItHit(clickedPane.getId().substring(4, 6), clickedPane);
            if (!myTurn) {
                client.sendMessage2("HIT " + clickedPane.getId().substring(4, 6));
            }
        }
    }

    private void didItHit(String coordinates, Pane clickedPane) {
        if (this.opponentsBoard[Integer.parseInt(String.valueOf(coordinates.charAt(1)))][Integer.parseInt(String.valueOf(coordinates.charAt(0)))]) {
            this.opponentsBoard[Integer.parseInt(String.valueOf(coordinates.charAt(1)))][Integer.parseInt(String.valueOf(coordinates.charAt(0)))] = false;
            clickedPane.setBackground(Background.fill(Paint.valueOf("red")));
        } else
            if (clickedPane.getBackground() == null) {
                clickedPane.setBackground(Background.fill(Paint.valueOf("yellow")));
                myTurn = false;
            }
    }

    @FXML
    private void readyMe() {
        AppGlobal.isGameStarted = true;
        readyButton.setVisible(false);
        waitLable.setVisible(true);
        client.sendMessage2( "READY_ME " + " >" + Arrays.deepToString(this.gameBoard));
       // startButton.setVisible(true);
    }

    private void bothReady() {
        waitLable.setVisible(false);
        waitForPlayer.setVisible(true);
    }
    private boolean checkWin() {
        boolean isWon = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.opponentsBoard[i][j]) {
                    isWon = false;
                    break;
                }
            }
        }
        return isWon;
    }


    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket != null)
                socket.close();
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}