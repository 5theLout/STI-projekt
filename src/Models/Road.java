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

    List<RoadField> fields = new ArrayList<>();

    public Road() {}

    public Road(List<RoadField> fields) {
        this.fields = fields;
    }

    public List<RoadField> getFields() {
        return fields;
    }

    public void setFields(List<RoadField> fields) {
        this.fields = fields;
    }

    public void addField(RoadField roadField) {
        fields.add(roadField);
    }

    public int getRoadLength() {
        return fields.size();
    }

    @Override
    public void draw(GraphicsContext gc) {
        for(RoadField roadField : fields) {
            roadField.draw(gc);
        }
    }

}
