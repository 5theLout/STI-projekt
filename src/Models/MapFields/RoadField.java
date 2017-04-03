package Models.MapFields;

import Models.Prototypes.Drawable;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RoadField implements Drawable {

    Struct positionFact;

    public RoadField(int xPos, int yPos){

        positionFact = new Struct("road_field", new Int(xPos), new Int(yPos));
    }


    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream("/RoadField.png"))), ((Int)(positionFact.getArg(0))).intValue(), ((Int)(positionFact.getArg(1))).intValue(), 50, 50);
    }

    @Override
    public int getXPos() {
        return 0;
    }

    @Override
    public int getYPos() {
        return 0;
    }
}
