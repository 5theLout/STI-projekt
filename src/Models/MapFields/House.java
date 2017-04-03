package Models.MapFields;

import Models.Garbages.Garbage;
import Models.Prototypes.Drawable;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-03-27.
 */

public class House implements Drawable {

    //int xPos;
    //int yPos;

    Struct positionFact;
    //Struct hasRubishFact = new Struct("has_rubbish", new Int(0), positionFact);

    List<Garbage> garbages;

    public House(int xPos, int yPos) {
        positionFact = new Struct("field_position", new Int(xPos), new Int(yPos));
        garbages = new ArrayList<>();
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream("/grass01.png"))), ((Int)(positionFact.getArg(0))).intValue(), ((Int)positionFact.getArg(1)).intValue(), 50, 50);
        gc.drawImage(new Image((getClass().getResourceAsStream("/HouseSimple.png"))), ((Int)(positionFact.getArg(0))).intValue()+5, ((Int)(positionFact.getArg(1))).intValue()+5, 40, 40);
        for(Garbage garbage : garbages) {
            gc.drawImage(garbage.getImage(), ((Int)(positionFact.getArg(0))).intValue()+5, ((Int)(positionFact.getArg(1))).intValue()+5, 15, 15);
        }
    }

    @Override
    public int getXPos() {
        return ((Int)(positionFact.getArg(0))).intValue();
    }

    @Override
    public int getYPos() {
        return ((Int)(positionFact.getArg(1))).intValue();
    }

    public void addGarbage(Garbage garbage) {
        this.garbages.add(garbage);
    }


}