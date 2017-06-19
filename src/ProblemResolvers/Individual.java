package ProblemResolvers;

import Models.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Individual {

    private List<Node> goalNodes = new ArrayList<>();

    private Node startNode;

    private int fitness = 0;

    public Individual(){}

    public Individual(List<Node> goalNodes, Node startNode) {
        for (Iterator<Node> it = goalNodes.iterator(); it.hasNext(); ) {
            Node node = it.next();
            this.goalNodes.add(new Node(new Position(node.getPosition().getXPos(), node.getPosition().getYPos())));
        }
        this.startNode = new Node(new Position(startNode.getPosition().getXPos(), startNode.getPosition().getYPos()));
    }

    public int getFitness() throws Exception {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    public List<Node> getGoalNodes() {
        //List<Node> goalNodes = new ArrayList<>();
        //for(Node node : this.goalNodes) {
        //    goalNodes.add(new Node(new Position(node.getPosition().getXPos(), node.getPosition().getYPos())));
        //}
        return this.goalNodes;
    }

    public void addGoalNode(Node node) {
        this.goalNodes.add(new Node(new Position(node.getPosition().getXPos(),node.getPosition().getYPos())));
    }

    public void repairIndividual(List<Node> availableGoalNodes) {

        List<Node> notUsedNodes = new ArrayList<>();
        for(Node availableGoalNode : availableGoalNodes) {
            boolean notUsed = true;
            for(Node goalNodeFromIndividual : this.getGoalNodes()) {
                if(goalNodeFromIndividual.getPosition().getXPos() == availableGoalNode.getPosition().getXPos() &&
                        goalNodeFromIndividual.getPosition().getYPos() == availableGoalNode.getPosition().getYPos()) {
                    notUsed = false;
                }
            }
            if(notUsed) notUsedNodes.add(new Node(new Position(availableGoalNode.getPosition().getXPos(), availableGoalNode.getPosition().getYPos())));
        }

        Iterator<Node> iter = this.getGoalNodes().iterator();

        List<Integer> nodeListIndexesToReplace = new ArrayList<>();

        while (iter.hasNext()) {
            Node nodeFromIndividual = iter.next();

            int duplicates = -1;
            for(Node anotherNodeFromIndividual : this.getGoalNodes()) {
                if(anotherNodeFromIndividual.getPosition().getXPos() == nodeFromIndividual.getPosition().getXPos() &&
                        anotherNodeFromIndividual.getPosition().getYPos() == nodeFromIndividual.getPosition().getYPos()) {
                    duplicates++;
                }
            }
            if(duplicates > 0) {
                int index = this.goalNodes.indexOf(nodeFromIndividual);
                nodeListIndexesToReplace.add(index);
                iter.remove();
                if(notUsedNodes.size() == 0) {
                    System.out.print("dcjsk");
                }
                //this.goalNodes.add(index, notUsedNodes.remove(0));
            }
        }
        for(Integer indexToReplace : nodeListIndexesToReplace) {
            this.goalNodes.add(indexToReplace, notUsedNodes.remove(0));
        }
        //this.goalNodes.addAll(notUsedNodes);

        //for(Node nodeFromIndividual : this.getGoalNodes()) {
        //    int duplicates = -1;
        //    for(Node anotherNodeFromIndividual : this.getCopyOfGoalNodes()) {
        //        if(anotherNodeFromIndividual.getPosition().getXPos() == nodeFromIndividual.getPosition().getXPos() &&
        //                anotherNodeFromIndividual.getPosition().getYPos() == nodeFromIndividual.getPosition().getYPos()) {
        //            duplicates++;
        //        }
        //    }
        //    if(duplicates > 0) {
        //        int index = this.goalNodes.indexOf(nodeFromIndividual);
        //        this.goalNodes.remove(index);
        //        if(notUsedNodes.size() == 0) {
        //            System.out.print("dcjsk");
        //        }
        //        this.goalNodes.add(index, notUsedNodes.get(0));
        //        notUsedNodes.remove(0);
        //    }
        //}
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }
}

