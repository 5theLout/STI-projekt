package ProblemResolvers;

import Models.Environment;
import Models.MapFields.GarbageColletionField;
import Models.MapFields.Intersection;
import Models.Position;
import Models.Road;
import PrologIntegrations.PrologEngineResolver;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dominik on 2017-05-25.
 */
public class AStar {

    Node startNode;

    Node goalNode;

    List<Node> nodes = new ArrayList<>();

    public AStar() {}

    public AStar(Node start, Node goal) {
        startNode = start;
        goalNode = goal;

    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getGoalNode() {
        return goalNode;
    }

    public void setGoalNode(Node goalNode) {
        this.goalNode = goalNode;
    }

    public void resolveNeighboursForNode(PrologEngineResolver prologEngineResolver, Node node) throws Exception {

            for(Node potentialNeighbour : nodes) {
                if (prologEngineResolver.solveSimpleQuery("neighbour(("
                        + node.getPosition().getXPos()
                        + ","
                        + node.getPosition().getYPos()
                        + "),("
                        + potentialNeighbour.getPosition().getXPos()
                        + ","
                        + potentialNeighbour.getPosition().getYPos()
                        + ")).")) {
                    node.addNeighbourNode(potentialNeighbour);
                }
            }
        }

    public void prepareNodesForJob(Environment environment) {

        for(Intersection intersection : environment.getIntersections()) {
            Node node = new Node(new Position(intersection.getCenterPosition().getXPos(),
                    intersection.getCenterPosition().getYPos()
            ));

            if(startNode.getPosition().getXPos() == intersection.getCenterPosition().getXPos()
                    && startNode.getPosition().getYPos() == intersection.getCenterPosition().getYPos()) {
                nodes.add(startNode);
            }
            else if(goalNode.getPosition().getXPos() == intersection.getCenterPosition().getXPos()
                    && goalNode.getPosition().getYPos() == intersection.getCenterPosition().getYPos()) {
                nodes.add(goalNode);
            } else nodes.add(node);
        }
        for(GarbageColletionField garbageColletionField : environment.getGarbageColletionFields()) {
            Node node = new Node(new Position(garbageColletionField.getCenterPosition().getXPos(),
                    garbageColletionField.getCenterPosition().getYPos()));

            if (startNode.getPosition().getXPos() == garbageColletionField.getCenterPosition().getXPos())
                if (startNode.getPosition().getYPos() == garbageColletionField.getCenterPosition().getYPos()) {
                    nodes.add(startNode);
                } else {
                    if (goalNode.getPosition().getXPos() == garbageColletionField.getCenterPosition().getXPos()
                            && goalNode.getPosition().getYPos() == garbageColletionField.getCenterPosition().getYPos()) {
                        nodes.add(goalNode);
                    } else nodes.add(node);
                }
            else if (goalNode.getPosition().getXPos() == garbageColletionField.getCenterPosition().getXPos()
                    && goalNode.getPosition().getYPos() == garbageColletionField.getCenterPosition().getYPos()) {
                nodes.add(goalNode);
            } else nodes.add(node);
        }

    }


    //for(Node node : nodes) {
        //    for(Node potentialNeighbour: nodes) {
        //        try {
        //            List<String> result = prologEngineResolver.solvePrologQuery(
        //                    "findroad((" + node.getPosition().getXPos() + "," + node.getPosition().getYPos() + "),(" + potentialNeighbour.getPosition().getXPos() + "," + potentialNeighbour.getPosition().getYPos() + ")");
        //            if(!result.isEmpty()) {
        //                node.addNeighbourNode(potentialNeighbour);
        //            }
        //        } catch (Exception e) {
//
        //        }
        //    }
        //}

    public class DefaultHashMap<K,V> extends HashMap<K,V> {
        protected V defaultValue;
        public DefaultHashMap(V defaultValue) {
            this.defaultValue = defaultValue;
        }
        @Override
        public V get(Object k) {
            return containsKey(k) ? super.get(k) : defaultValue;
        }
    }

    public List<Node> solvePath(PrologEngineResolver prologEngineResolver) throws Exception {
        List<Node> closedSet = new ArrayList<>();

        List<Node> openSet = new ArrayList<>();
        openSet.add(startNode);

        Map<Node, Node> cameFrom = new HashMap<>();

        Map<Node,Double> gScore = new DefaultHashMap<>(Double.MAX_VALUE);
        gScore.put(startNode, 0.0);

        Map<Node, Double> fScore = new DefaultHashMap<Node, Double>(Double.MAX_VALUE);
        fScore.put(startNode, heuristicCostEstimate(startNode, goalNode));

        while(!openSet.isEmpty()) {

            Node current = new Node(); //declaring current node!
            for(Node nodeFromOpenSet : openSet) {
                if(fScore.get(nodeFromOpenSet) < fScore.get(current)) {
                    current = nodeFromOpenSet;
                }
            }
            if(current == goalNode) return reconstructPath(cameFrom, current);

            openSet.remove(current);
            closedSet.add(current);
            resolveNeighboursForNode(prologEngineResolver, current);
            for(Node neighbourNode : current.getNeighbourNodes()) {
                if(closedSet.contains(neighbourNode)) {
                    continue;
                }
                double tentativeGScore = gScore.get(current) + distBetween(prologEngineResolver, current, neighbourNode);
                if(!openSet.contains(neighbourNode)) openSet.add(neighbourNode);
                else if(tentativeGScore >= gScore.get(neighbourNode) ) continue;

                cameFrom.put(neighbourNode, current);
                gScore.put(neighbourNode,tentativeGScore);
                fScore.put(neighbourNode, gScore.get(neighbourNode) + heuristicCostEstimate(neighbourNode, goalNode));
            }

        }



        return null;

    }

    private Double distBetween(PrologEngineResolver prologEngineResolver, Node current, Node neighbourNode) {
        for(int i=0; i <Integer.MAX_VALUE; i++) {
            if (prologEngineResolver.solveSimpleQuery("roadlength(("
                    + current.getPosition().getXPos()
                    + ","
                    + current.getPosition().getYPos()
                    + "),("
                    + neighbourNode.getPosition().getXPos()
                    + ","
                    + neighbourNode.getPosition().getYPos()
                    + "),"
                    + i
                    + ").")) return (double)i*50;
        }

        return (double)Integer.MAX_VALUE;
    }

    private List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> totalPath = new ArrayList<>();

        totalPath.add(current);

        while(cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }

        return totalPath;
    }

    private double heuristicCostEstimate(Node x, Node y) {
        return (Math.sqrt(Math.pow(x.getPosition().getXPos() - y.getPosition().getXPos(), 2) +
                    Math.pow(x.getPosition().getYPos() - y.getPosition().getYPos(), 2)));
    }

}
