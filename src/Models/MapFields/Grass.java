package Models.MapFields;

import Models.Position;
import Models.Prototypes.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Grass implements Drawable{

    private Position centerPosition;

    public Grass(int xPos, int yPos){
        centerPosition = new Position(xPos, yPos);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream("/Grass.png"))), centerPosition.getXPos() - 25, centerPosition.getYPos() - 25, 50, 50);
    }

}