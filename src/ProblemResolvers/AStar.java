package ProblemResolvers;

import Models.Environment;
import Models.MapFields.GarbageColletionField;
import Models.Position;
import Models.Road;
import PrologIntegrations.PrologEngineResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-05-25.
 */
public class AStar {

    Node startNode;

    Node goalNode;

    List<Node> visitedNodes = new ArrayList<>();

    List<Node> notVisitedNodes = new ArrayList<>();

    List<Road> roads = new ArrayList<>();

    public AStar(Node start, Node goal) {
        notVisitedNodes.add(start);
        goalNode = goal;

    }

    public void setVisitedNodes(List<Node> visitedNodes) {
        this.visitedNodes = visitedNodes;
    }

    public void setNotVisitedNodes(List<Node> notVisitedNodes) {
        this.notVisitedNodes = notVisitedNodes;
    }

    public void searchPath(Environment environment) {
        for (GarbageColletionField garbageColletionField : environment.getGarbageColletionFields()) {
            notVisitedNodes.add( new Node(garbageColletionField.getCenterPosition()) );
        }

        startNode.g_score = 0;

        Node x = startNode;

        while(!notVisitedNodes.isEmpty()) {
            for (Node notVisitedNode : notVisitedNodes) {
                if (x.f_score > notVisitedNode.f_score) x = notVisitedNode;
            }
        }

    }

}
