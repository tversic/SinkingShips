package com.sinkingships.utility;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Draggable {
    private int mouseOffSetXHorizontal = 820;
    private int mouseOffSetXVertical = 1065;
    private int mouseOffsetYHorizontal = 40;

    private int mouseOffsetYVertical = -200;

    //704 width limit
    //duljina - 8 * 101
    public void makeDraggable(Node node){
        Integer sizeOfShip = 0;

        if(node instanceof ImageView){
            sizeOfShip = Integer.valueOf(node.getId().substring(node.getId().length() - 1));
        }
        Integer finalSizeOfShip = sizeOfShip;
        node.setOnMouseDragged(mouseEvent -> {
            if (node.getRotate() == 0 || node.getRotate() == 180) {
                if ((mouseEvent.getSceneX() > 820 && ((mouseEvent.getSceneX()) < (((8 - finalSizeOfShip) * 101) + 840)))
                        && mouseEvent.getSceneY() > 33
                        && mouseEvent.getSceneY() < 850) {
                    node.setLayoutX(mouseEvent.getSceneX() - mouseOffSetXHorizontal);
                    node.setLayoutY(mouseEvent.getSceneY() - mouseOffsetYHorizontal);
                }
            }else{
                if ((mouseEvent.getSceneX() > 840 && ((mouseEvent.getSceneX()) < 1580))
                        && mouseEvent.getSceneY() > 0
                        && mouseEvent.getSceneY() < ((9 - finalSizeOfShip) * 100)) {
                    node.setLayoutX(mouseEvent.getSceneX() - mouseOffSetXVertical);
                    node.setLayoutY(mouseEvent.getSceneY() - mouseOffsetYVertical);
                }
            }
            System.out.println("X: " + mouseEvent.getSceneX() + " Y: " + mouseEvent.getSceneY());
        });
    }


}
