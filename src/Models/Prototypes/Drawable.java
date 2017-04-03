package Models.Prototypes;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    void draw(GraphicsContext gc);

    int getXPos();

    int getYPos();
}
