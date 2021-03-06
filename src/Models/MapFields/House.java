package Models.MapFields;

import Models.Garbages.Garbage;
import Models.Position;
import Models.Prototypes.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class House implements Drawable {

    Position centerPosition;

    List<Garbage> garbages;

    public House(int xPos, int yPos) {
        garbages = new ArrayList<>();

        centerPosition = new Position(xPos, yPos);

    }

    public House(int xPos, int yPos, Image img) {
        garbages = new ArrayList<>();

        centerPosition = new Position(xPos, yPos);

        this.image = img;
    }

    Image image;

    public void draw(GraphicsContext gc) {
        if(image != null) {
            gc.drawImage(new Image((getClass().getResourceAsStream("/Grass.png"))), centerPosition.getXPos() - 25, centerPosition.getYPos() - 25, 50, 50);
            gc.drawImage(image, centerPosition.getXPos() + 5 - 20, centerPosition.getYPos() + 5 - 20, 40, 40);
        } else {
            gc.drawImage(new Image((getClass().getResourceAsStream("/Grass.png"))), centerPosition.getXPos() - 25, centerPosition.getYPos() - 25, 50, 50);
            gc.drawImage(new Image((getClass().getResourceAsStream("/House.png"))), centerPosition.getXPos() + 5 - 20, centerPosition.getYPos() + 5 - 20, 40, 40);
        }
        for(Garbage garbage : garbages) {
            gc.drawImage(garbage.getImage(), centerPosition.getXPos() + 5, centerPosition.getYPos() + 5, 15, 15);
        }
    }

    public void addGarbage(Garbage garbage) {
        this.garbages.add(garbage);
    }

    public Position getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(Position centerPosition) {
        this.centerPosition = centerPosition;
    }

    public List<Garbage> getGarbages() {
        return garbages;
    }

    public void setGarbages(List<Garbage> garbages) {
        this.garbages = garbages;
    }
}