package Models.Garbages;

import javafx.scene.image.Image;

public class Bottle extends Garbage {

    Image image = new Image((getClass().getResourceAsStream("/PlasticBoxWithPaper.png")));

    public Bottle(int amount) {
        this.setAmount(amount);
    }

    @Override
    public Image getImage() {
        return this.image;
    }
}
