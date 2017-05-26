package ProblemResolvers;

import Models.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-05-25.
 */
public class Node {

    Position position;

    public List<Node> neighbourNodes = new ArrayList<>();

    public int g_score;

    public double f_score;

    public Node(Position position) {
        this.position = position;
       // f_score = Math.sqrt(Math.pow(position.getXPos(),2) + Math.pow(position.getYPos(),2));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
