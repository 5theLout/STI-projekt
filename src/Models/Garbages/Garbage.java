package Models.Garbages;

import javafx.scene.image.Image;

public class Garbage {

    private int amount;

    Image image =  new Image((getClass().getResourceAsStream("/PlasticBoxWithPaper.png")));

    public Garbage() {}

    public Garbage(int amount) {
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount(int amount) {
        return this.amount;
    }

    public Image getImage() {
        return this.image;
    }

}
