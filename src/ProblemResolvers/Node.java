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

    public Node() {}

    public Node(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void addNeighbourNode(Node node) {
        neighbourNodes.add(node);
    }

    public List<Node> getNeighbourNodes() {
        return neighbourNodes;
    }

    public void setNeighbourNodes(List<Node> neighbourNodes) {
        this.neighbourNodes = neighbourNodes;
    }

}
