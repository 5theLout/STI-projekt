package Models.MapFields;

import Models.Position;
import Models.Prototypes.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by Dominik on 2017-05-25.
 */
public class GarbageColletionField extends Field implements Drawable {

    String image = new String("/HouseGCPointField.png");

    public GarbageColletionField(int xPos, int yPos){
        super(xPos, yPos);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image((getClass().getResourceAsStream(image))), centerPosition.getXPos() - 25, centerPosition.getYPos() - 25, 50, 50);
    }

    public Position getCenterPosition() {
        return centerPosition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
