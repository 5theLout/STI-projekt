package Models;

import Models.MapFields.GarbageColletionField;
import Models.MapFields.Intersection;
import Models.MapFields.RoadField;
import Models.Prototypes.Drawable;
import ProblemResolvers.Node;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Environment {

    List<Drawable> mapFields = new ArrayList<>();

    List<GarbageColletionField> garbageColletionFields = new ArrayList<>();

    List<Road> roads = new ArrayList<>();

    GarbageTruck garbageTruck;

    List<Intersection> intersections = new ArrayList<>();

    List<Position> criticalPositions = new ArrayList<>();

    public Environment() {}

    public void draw(GraphicsContext gc) {
        drawMapFields(gc);
        drawRoads(gc);
        drawGarbageCollectionFields(gc);

        //garbageTruck.draw(gc);
    }

    public void drawRoads(GraphicsContext gc) {
        for (Road road : roads) {
            road.draw(gc);
        }
    }

    public void drawMapFields(GraphicsContext gc) {
        for (Drawable drawable : mapFields) {
            drawable.draw(gc);
        }
    }

    public void drawGarbageCollectionFields(GraphicsContext gc) {
        for (GarbageColletionField garbageColletionField : garbageColletionFields) {
            garbageColletionField.draw(gc);
        }
    }

    public void addMapEntity(Drawable drawable) {
        mapFields.add(drawable);
    }

    public void addRoadEntity(Road road) { roads.add(road); }

    public void addGarbageTruck(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
    }

    public void addHouseGCPointEntity(GarbageColletionField garbageColletionField) { garbageColletionFields.add(garbageColletionField); }

    public void addIntersectionEntity(Intersection intersection) { intersections.add(intersection); }

    public List<GarbageColletionField> getGarbageColletionFields() {
        return garbageColletionFields;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public GarbageTruck getGarbageTruck() {
        return garbageTruck;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<Intersection> intersections) {
        this.intersections = intersections;
    }

    public RoadField searchForRoadField(Position position) {

        for(Road road : roads) {
            for(RoadField roadField : road.getRoadFields()) {
                if(roadField.getCenterPosition().getXPos() == position.getXPos() && roadField.getCenterPosition().getYPos() == position.getYPos()) {
                    return roadField;
                }
            }
        }
        return null;
    }

    public GarbageColletionField searchForGarbageCollectionField(Position position) {
        for(GarbageColletionField garbageColletionField : getGarbageColletionFields()) {
            if(garbageColletionField.getCenterPosition().getXPos() == position.getXPos() && garbageColletionField.getCenterPosition().getYPos() == position.getYPos()) {
                return garbageColletionField;
            }
        }
        return null;
    }

    public List<Road> loadRoadsBasedOnResult(List<Node> result) {
        List<Road> roadsToGoForGarbageTruck = new ArrayList<>();

        for(int i = 0; i < result.size() - 1; i++) {
            for(Road road : roads) {
                List<RoadField> roadFields = road.getRoadFields();
                if(
                        roadFields.get(0).getCenterPosition().getXPos() == result.get(i).getPosition().getXPos()
                                && roadFields.get(0).getCenterPosition().getYPos() == result.get(i).getPosition().getYPos()
                                && roadFields.get(roadFields.size() - 1).getCenterPosition().getXPos() == result.get(i + 1).getPosition().getXPos()
                                && roadFields.get(roadFields.size() - 1).getCenterPosition().getYPos() == result.get(i + 1).getPosition().getYPos()) {

                    roadsToGoForGarbageTruck.add(road);
                }
            }
        }

        return roadsToGoForGarbageTruck;
    }
}