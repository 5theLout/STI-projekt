package Models;

import Models.MapFields.RoadField;
import Models.Prototypes.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-03-27.
 */
public class GarbageTruck implements Drawable {

    Position centerPosition;

    RoadField previousCorespondingRoadField;

    MovingVector movingVector;

    private int width;
    private int height;

    public boolean jobDone = true;
    public boolean isInGarbageCollectionField = false;

    List<Position> currentRoute = new ArrayList<>();

    List<Road> currentRoadsList = new ArrayList<>();

    public GarbageTruck(int xPos, int yPos) {
        centerPosition = new Position(xPos, yPos);
        this.width = 30;
        this.height = 10;

        movingVector = new MovingVector(0,0);
    }

    public int resolveTruckDirection() {
        if(movingVector.getX() > 0) return 180;
        else if(movingVector.getX() < 0) return 0;
        else if(movingVector.getY() > 0) return -90;
        else if(movingVector.getY() < 0) return 90;
        else return 0;
    }

    public void draw(GraphicsContext gc) {
        //gc.drawImage(new Image((getClass().getResourceAsStream("/GarbageTruck.png"))), ((Int)positionFact.getArg(0)).intValue(), ((Int)positionFact.getArg(1)).intValue(), 60, 20);
        drawRotatedImage(gc, new Image((getClass().getResourceAsStream("/GarbageTruck.png"))), resolveTruckDirection(), centerPosition.getXPos() - 15, centerPosition.getYPos() - 15);
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * Draws an image on a graphics context.
     *
     * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
     *   (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
     *
     * @param gc the graphics context the image is to be drawn on.
     * @param angle the angle of rotation.
     * @param tlpx the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
     * @param tlpy the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
     */
    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + width / 2, tlpy + height / 2);
        gc.drawImage(image, tlpx, tlpy, width, height);
        gc.restore(); // back to original state (before rotation)
    }

    public void beginJob(List<Road> roadsToGoThrough, Environment environment) {
        jobDone = false;

        currentRoadsList = roadsToGoThrough;

        for(Road road : roadsToGoThrough) {
            for(RoadField roadField : road.getRoadFields()) {
                currentRoute.add(roadField.getCenterPosition());
            }
        }

        this.setCenterPosition(currentRoute.get(0));
        previousCorespondingRoadField = environment.searchForRoadField(currentRoute.get(0));
    }

    public void goToNextPosition(Environment environment) {
        previousCorespondingRoadField = environment.searchForRoadField(currentRoute.get(0));
        currentRoute.remove(0);
        if(currentRoute.isEmpty()) jobDone = true;
        else this.centerPosition = currentRoute.get(0);
        movingVector = new MovingVector(centerPosition.getXPos() - previousCorespondingRoadField.getCenterPosition().getXPos(), centerPosition.getYPos() - previousCorespondingRoadField.getCenterPosition().getYPos());

    }

    public List<Road> getCurrentRoadsList() {
        return currentRoadsList;
    }

    public void setCurrentRoadsList(List<Road> currentRoadsList) {
        this.currentRoadsList = currentRoadsList;
    }

    public RoadField getPreviousCorespondingRoadField() {
        return previousCorespondingRoadField;
    }

    public void setPreviousCorespondingRoadField(RoadField previousCorespondingRoadField) {
        this.previousCorespondingRoadField = previousCorespondingRoadField;
    }

    public Position getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(Position centerPosition) {
        this.centerPosition = centerPosition;
    }
}
