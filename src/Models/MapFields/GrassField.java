package Models.MapFields;

import Models.Position;
import Models.Prototypes.Drawable;
import alice.tuprolog.Double;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GrassField implements Drawable{

    private Position centerPosition;

    public GrassField(int xPos, int yPos){
        centerPosition = new Position(xPos, yPos);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream("/grass01.png"))), centerPosition.getXPos() - 25, centerPosition.getYPos() - 25, 50, 50);
    }

}