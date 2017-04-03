package Models;

import Models.Prototypes.Drawable;
import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * Created by Dominik on 2017-03-27.
 */
public class GarbageTruck implements Drawable {

    private Struct positionFact;

    private int width;
    private int height;

    public GarbageTruck(int xPos, int yPos) {
        positionFact = new Struct("garbage_truck", new Int(xPos), new Int(yPos));
        this.width = 60;
        this.height = 20;
    }

    public void draw(GraphicsContext gc) {
        //gc.drawImage(new Image((getClass().getResourceAsStream("/GarbageTruck.png"))), ((Int)positionFact.getArg(0)).intValue(), ((Int)positionFact.getArg(1)).intValue(), 60, 20);
        drawRotatedImage(gc, new Image((getClass().getResourceAsStream("/GarbageTruck.png"))), 10, ((Int)(positionFact.getArg(0))).intValue(), ((Int)positionFact.getArg(1)).intValue());
    }

    @Override
    public int getXPos() {
        return 0;
    }

    @Override
    public int getYPos() {
        return 0;
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
}
