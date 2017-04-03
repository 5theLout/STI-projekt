package Models.MapFields;

import Models.Prototypes.Drawable;
import alice.tuprolog.Double;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GrassField implements Drawable{

    private Struct positionFact;

    public GrassField(int xPos, int yPos){
        positionFact = new Struct("grass_field", new Int(xPos), new Int(yPos));
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream("/grass01.png"))), ((Int)(positionFact.getArg(0))).intValue(), ((Int)positionFact.getArg(1)).intValue(), 50, 50);
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