package com.sinkingships.gameObjects;

import javafx.scene.image.ImageView;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ship {
    private ImageView shipImage;
    private boolean isActive = false;
    private String shipName;
}
