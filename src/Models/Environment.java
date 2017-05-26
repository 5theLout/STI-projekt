package Models;

import Models.MapFields.GarbageColletionField;
import Models.Prototypes.Drawable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Environment {

    List<Drawable> mapFields = new ArrayList<>();

    List<GarbageColletionField> garbageColletionFields = new ArrayList<>();

    List<Road> roads = new ArrayList<>();

    GarbageTruck garbageTruck;

    public Environment() {}

    public void draw(GraphicsContext gc) {
        for (Drawable drawable : mapFields) {
            drawable.draw(gc);
        }
        for (Road road : roads) {
            road.draw(gc);
        }
        for (GarbageColletionField garbageColletionField : garbageColletionFields) {
            garbageColletionField.draw(gc);
        }
        garbageTruck.draw(gc);
    }

    public void addMapEntity(Drawable drawable) {
        mapFields.add(drawable);
    }

    public void addRoadEntity(Road road) { roads.add(road); }

    public void addGarbageTruck(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
    }

    public void addHouseCGPointEntity(GarbageColletionField garbageColletionField) { garbageColletionFields.add(garbageColletionField); }

    public List<GarbageColletionField> getGarbageColletionFields() {
        return garbageColletionFields;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public GarbageTruck getGarbageTruck() {
        return garbageTruck;
    }
}