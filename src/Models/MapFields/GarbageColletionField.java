package Models.MapFields;

import Models.Position;
import Models.Prototypes.Drawable;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by Dominik on 2017-05-25.
 */
public class GarbageColletionField implements Drawable{

    Position centerPosition;

    public GarbageColletionField(int xPos, int yPos){

        centerPosition = new Position(xPos, yPos);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream("/HouseCGPointField.png"))), centerPosition.getXPos() - 25, centerPosition.getYPos() - 25, 50, 50);
    }

    public Position getCenterPosition() {
        return centerPosition;
    }
}
