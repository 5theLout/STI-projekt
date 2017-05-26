package Models.MapFields;

import Models.Position;
import Models.Prototypes.Drawable;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RoadField implements Drawable {

    private Position centerPosition;

    boolean isIntersection = false;

    public RoadField(int xPos, int yPos){

        centerPosition = new Position(xPos, yPos);
    }


    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream("/RoadField.png"))), centerPosition.getXPos() - 25, centerPosition.getYPos() - 25, 50, 50);
    }

    public boolean isIntersection() {
        return isIntersection;
    }

    public void setIntersection(boolean intersection) {
        isIntersection = intersection;
    }
}
