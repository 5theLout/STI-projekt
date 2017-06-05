package Models.MapFields;

import Models.Position;
import Models.Prototypes.Drawable;

/**
 * Created by Dominik on 2017-06-01.
 */
public abstract class Field {

    Position centerPosition;

    public Field() {}

    public Field(int xPos, int yPos) {
        this.centerPosition = new Position(xPos, yPos);
    }

    public Position getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(Position centerPosition) {
        this.centerPosition = centerPosition;
    }
}
