package ProblemResolvers;

import Models.Environment;
import Models.MapFields.RoadField;
import Models.Road;
import PrologIntegrations.PrologEngineResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dominik on 2017-06-04.
 */
public class GeneticsAlgorithm {

    List<List<Node>> population;

    Environment environment;

    PrologEngineResolver prologEngineResolver;

    List<PopulationSpecimen> populationSpecimenList;


    int populationSize;

    public GeneticsAlgorithm(List<Node> nodes, Environment environment, PrologEngineResolver prologEngineResolver) {
        this.environment = environment;
        this.prologEngineResolver = prologEngineResolver;

        //population = generatePerm(nodes);
    }

    public void printPopulationSpecimens() {
        for(List<Node> nodes : population) {
            for(Node node : nodes) {
                System.out.print("(" + node.getPosition().getXPos() + "," + node.getPosition().getYPos() + "), ");
            }
            System.out.println();
        }
    }

    //public List<List<Node>> generatePerm(List<Node> original) {
    //    if (original.size() == 0) {
    //        List<List<Node>> result = new ArrayList<List<Node>>();
    //        result.add(new ArrayList<Node>());
    //        return result;
    //    }
    //    Node firstElement = original.remove(0);
    //    List<List<Node>> returnValue = new ArrayList<List<Node>>();
    //    List<List<Node>> permutations = generatePerm(original);
    //    for (List<Node> smallerPermutated : permutations) {
    //        for (int index=0; index <= smallerPermutated.size(); index++) {
    //            List<Node> temp = new ArrayList<Node>(smallerPermutated);
    //            temp.add(index, firstElement);
    //            returnValue.add(temp);
    //        }
    //    }
    //    return returnValue;
    //}

    //public PopulationSpecimen runAlgorithm() throws Exception {
//
    //    for(int i=0; i<100; i++) {
//
    //        List<PopulationSpecimen> rank = new ArrayList<>();
//
    //        preparePopulationSpecimens();
    //        for(PopulationSpecimen populationSpecimen : populationSpecimenList) {
    //            for(PopulationSpecimen populationSpecimenFromRank : rank) {
    //                if(populationSpecimen.getPhenotype() < populationSpecimenFromRank.getPhenotype()) {
    //                    rank.add(rank.indexOf(populationSpecimenFromRank), populationSpecimen);
    //                    break;
    //                }
    //                rank.add(populationSpecimen);
    //            }
    //        }
//
    //        populationSpecimenList = new ArrayList<>();
//
    //        for(int j=0; j < rank.size()/2; j++) {
    //            populationSpecimenList.add(j, rank.get(j));
    //        }
//
//
    //    }
    //}

    //public void generate

    public void preparePopulationSpecimens() throws Exception {
        for(List<Node> nodes : population) {
            int phenotype = measurePhenotype(nodes);
            PopulationSpecimen populationSpecimen = new PopulationSpecimen(nodes, phenotype);
            populationSpecimenList.add(populationSpecimen);
        }
    }

    public void setPopulationSize() {
        this.populationSize = populationSpecimenList.size();
    }

    public int measurePhenotype(List<Node> goalNodes) throws Exception {
        int phenotype = 0;

        List<Node> allJobResult = new ArrayList<>();
        Node startNode = goalNodes.get(0);
        goalNodes.remove(0);

        for(Node goalNode : goalNodes) {
            AStar aStar = new AStar(goalNode,
                    startNode);

            aStar.prepareNodesForJob(environment);

            //------------------------------------------------------------------------------------------------------------------------
            //System.out.println("(" + allNodes.get(0).getPosition().getXPos() + "," + allNodes.get(0).getPosition().getYPos() + ")");

            //System.out.println("(" + allNodes.get(6).getPosition().getXPos() + "," + allNodes.get(6).getPosition().getYPos() + ")");
            //------------------------------------------------------------------------------------------------------------------------

            List<Node> result = aStar.solvePath(this.prologEngineResolver);

            //------------------------------------------------------------------------------------------------------------------------
            for(int i = 0; i<result.size(); i++) {
                //System.out.println("(" + result.get(i).getPosition().getXPos() + "," + result.get(i).getPosition().getYPos() + ")");
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
            phenotype += road.getRoadLength();
        }

        return phenotype;
    }



    //public void selectPopulationForNextGeneration(Node startNode) throws Exception {
//
    //    HashMap<PopulationSpecimen, Integer> populationSpecimensScores = new HashMap<>();
//
    //    for(PopulationSpecimen populationSpecimen : population) {
    //        int phenotype = measurePhenotypeForSpecimen(startNode, populationSpecimen);
    //        populationSpecimensScores.put(populationSpecimen, phenotype);
    //    }
//
    //    List<PopulationSpecimen> populationRank = new ArrayList<>();
//
    //    for(PopulationSpecimen populationSpecimen : populationSpecimensScores.keySet()) {
    //        if (populationRank.isEmpty()) populationRank.add(populationSpecimen);
    //        else {
    //            for(PopulationSpecimen othPopulationSpecimen : populationRank) {
    //                if (populationSpecimensScores.get(populationSpecimen) < populationSpecimensScores.get(othPopulationSpecimen)) {
    //                    populationRank.add(populationRank.indexOf(othPopulationSpecimen), populationSpecimen);
    //                }
    //            }
    //        }
//
    //    }
//
    //    population = new ArrayList<PopulationSpecimen>();
//
    //    for(int i = 0; i < populationRank.size()/2; i++) {
    //        population.add(populationRank.get(i));
    //    }
    //}

}
