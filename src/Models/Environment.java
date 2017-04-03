package Models;

import Models.Prototypes.Drawable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Environment {

    List<Drawable> mapFields = new ArrayList<>();

    GarbageTruck garbageTruck;

    public Environment() {}

    public void draw(GraphicsContext gc) {
        for (Drawable drawable : mapFields) {
            drawable.draw(gc);
        }
        garbageTruck.draw(gc);
    }

    public void addMapEntity(Drawable drawable) {
        mapFields.add(drawable);
    }

    public void addGarbageTruck(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
    }

}