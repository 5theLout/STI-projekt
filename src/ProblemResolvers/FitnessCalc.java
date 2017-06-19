package ProblemResolvers;

import Models.Environment;
import Models.MapFields.RoadField;
import Models.Position;
import Models.Road;
import PrologIntegrations.PrologEngineResolver;

import java.util.ArrayList;
import java.util.List;

public class FitnessCalc {

    static int maxFitness = Integer.MAX_VALUE;

    static Environment environment;

    static PrologEngineResolver prologEngineResolver;

    public static void setEnvironment(Environment env, PrologEngineResolver pre) {
        environment = env;
        prologEngineResolver = pre;
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static int getFitness(Individual individual) throws Exception {
        int fitness = 0;
        List<Node> goalNodes = individual.getGoalNodes();
//
        //List<Node> notUsedNodes = new ArrayList<>();
        //for(Node goalNode : goalNodes) {
        //    boolean notUsed = true;
        //    for(Node goalNodeFromIndividual : individual.getGoalNodes()) {
        //        if(goalNodeFromIndividual.getPosition().getXPos() == goalNode.getPosition().getXPos() &&
        //                goalNodeFromIndividual.getPosition().getYPos() == goalNode.getPosition().getYPos()) {
        //            notUsed = false;
        //        }
        //    }
        //    if(notUsed) notUsedNodes.add(new Node(new Position(goalNode.getPosition().getXPos(), goalNode.getPosition().getYPos())));
        //}
//
        //for(Node nodeFromIndividual : individual.getGoalNodes()) {
        //    int duplicates = -1;
        //    for(Node anotherNodeFromIndividual : individual.getGoalNodes()) {
        //        if(anotherNodeFromIndividual.getPosition().getXPos() == nodeFromIndividual.getPosition().getXPos() &&
        //                anotherNodeFromIndividual.getPosition().getYPos() == nodeFromIndividual.getPosition().getYPos()) {
        //            duplicates++;
        //        }
        //    }
        //    if(duplicates > 0) {
        //        int index = individual.getGoalNodes().indexOf(nodeFromIndividual);
        //        individual.getGoalNodes().remove(index);
        //        if(notUsedNodes.size() == 0) {
        //            System.out.print("dcjsk");
        //        }
        //        if(index != 0) individual.getGoalNodes().add(index, notUsedNodes.get(0));
        //        else individual.getGoalNodes().add(notUsedNodes.get(0));
        //        notUsedNodes.remove(0);
        //    }
        //}

        List<Node> allJobResult = new ArrayList<>();
        Node startNode = individual.getStartNode();

        for(Node goalNode : goalNodes) {
            AStar aStar = new AStar(goalNode,
                    startNode);

            aStar.prepareNodesForJob(environment);

            //------------------------------------------------------------------------------------------------------------------------
            //System.out.println("(" + allNodes.get(0).getPosition().getXPos() + "," + allNodes.get(0).getPosition().getYPos() + ")");

            //System.out.println("(" + allNodes.get(6).getPosition().getXPos() + "," + allNodes.get(6).getPosition().getYPos() + ")");
            //------------------------------------------------------------------------------------------------------------------------

            List<Node> result = aStar.solvePath(prologEngineResolver);
            if(result == null) {
                System.out.print("cdsnjk");
            }
            //------------------------------------------------------------------------------------------------------------------------
            for(int i = 0; i<result.size(); i++) {
            //    System.out.println("(" + result.get(i).getPosition().getXPos() + "," + result.get(i).getPosition().getYPos() + ")");
            }
            //------------------------------------------------------------------------------------------------------------------------

            allJobResult.addAll(result);
            startNode = goalNode;
        }

        List<Road> allRoads = environment.getRoads();
        List<Road> roadsToGoForGarbageTruck = new ArrayList<>();

        for(int i = 0; i < allJobResult.size() - 1; i++) {
            for(Road road : allRoads) {
                List<RoadField> roadFields = road.getRoadFields();
                if(
                        roadFields.get(0).getCenterPosition().getXPos() == allJobResult.get(i).getPosition().getXPos()
                                && roadFields.get(0).getCenterPosition().getYPos() == allJobResult.get(i).getPosition().getYPos()
                                && roadFields.get(roadFields.size() - 1).getCenterPosition().getXPos() == allJobResult.get(i + 1).getPosition().getXPos()
                                && roadFields.get(roadFields.size() - 1).getCenterPosition().getYPos() == allJobResult.get(i + 1).getPosition().getYPos()) {

                    roadsToGoForGarbageTruck.add(road);
                }
            }
        }

        for(Road road : roadsToGoForGarbageTruck) {
            fitness += road.getRoadLength();
        }

        if(fitness < maxFitness) maxFitness = fitness;

        return fitness;
    }

    public static int getMaxFitness() {
        return maxFitness;
    }
}