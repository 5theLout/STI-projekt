package Models;

import Models.MapFields.RoadField;
import Models.Prototypes.Drawable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-05-24.
 */
public class Road implements Drawable{

    List<RoadField> roadFields = new ArrayList<>();

    public Road() {}

    public Road(List<RoadField> roadFields) {
        this.roadFields = roadFields;
    }

    public List<RoadField> getRoadFields() {
        return roadFields;
    }

    public void setRoadFields(List<RoadField> roadFields) {
        this.roadFields = roadFields;
    }

    public void addField(RoadField roadField) {
        roadFields.add(roadField);
    }

    public int getRoadLength() {
        return roadFields.size();
    }

    @Override
    public void draw(GraphicsContext gc) {
        for(RoadField roadField : roadFields) {
            roadField.draw(gc);
        }
    }

}
