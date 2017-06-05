package ProblemResolvers;

import Models.Environment;
import Models.Road;
import PrologIntegrations.PrologEngineResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dominik on 2017-06-04.
 */
public class GeneticsAlgorithm {

    List<PopulationSpecimen> population;

    Environment environment;

    PrologEngineResolver prologEngineResolver;

    public GeneticsAlgorithm(List<Node> nodes, Environment environment, PrologEngineResolver prologEngineResolver) {
        this.environment = environment;
        this.prologEngineResolver = prologEngineResolver;

        //population = generatePerm(nodes);
    }

    public List<PopulationSpecimen> generatePerm(PopulationSpecimen original) {
        if (original.getNodes().size() == 0) {
            List<PopulationSpecimen> result = new ArrayList<>();
            result.add(new PopulationSpecimen(new ArrayList<Node>()));
            return result;
        }
        Node firstElement = original.getNodes().remove(0);
        List<PopulationSpecimen> returnValue = new ArrayList<>();
        List<PopulationSpecimen> permutations = generatePerm(original);
        for (PopulationSpecimen smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.getNodes().size(); index++) {
                PopulationSpecimen temp = smallerPermutated;
                temp.getNodes().add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    public int measurePhenotypeForSpecimen(Node startNode, PopulationSpecimen populationSpecimen) throws Exception {
        int phenotype = 0;

        List<Node> allJobResult = new ArrayList<>();

        for(Node goalNode : populationSpecimen.getNodes()) {
            AStar aStar = new AStar(goalNode,
                    startNode);

            aStar.prepareNodesForJob(environment);

            List<Node> result = aStar.solvePath(environment, prologEngineResolver);

            //------------------------------------------------------------------------------------------------------------------------
            for(int i = 0; i<result.size(); i++) {
                System.out.println("(" + result.get(i).getPosition().getXPos() + "," + result.get(i).getPosition().getYPos() + ")");
            }
            //------------------------------------------------------------------------------------------------------------------------

            allJobResult.addAll(result);
            startNode = goalNode;
        }

        List<Road> roadsToGoForGarbageTruck = environment.loadRoadsBasedOnResult(allJobResult);

        for(Road road : roadsToGoForGarbageTruck) {
            phenotype += road.getRoadLength();
        }

        return phenotype;
    }

    public void selectPopulationForNextGeneration(Node startNode) throws Exception {

        HashMap<PopulationSpecimen, Integer> populationSpecimensScores = new HashMap<>();

        for(PopulationSpecimen populationSpecimen : population) {
            int phenotype = measurePhenotypeForSpecimen(startNode, populationSpecimen);
            populationSpecimensScores.put(populationSpecimen, phenotype);
        }

        List<PopulationSpecimen> populationRank = new ArrayList<>();

        for(PopulationSpecimen populationSpecimen : populationSpecimensScores.keySet()) {
            if (populationRank.isEmpty()) populationRank.add(populationSpecimen);
            else {
                for(PopulationSpecimen othPopulationSpecimen : populationRank) {
                    if (populationSpecimensScores.get(populationSpecimen) < populationSpecimensScores.get(othPopulationSpecimen)) {
                        populationRank.add(populationRank.indexOf(othPopulationSpecimen), populationSpecimen);
                    }
                }
            }

        }

        population = new ArrayList<PopulationSpecimen>();

        for(int i = 0; i < populationRank.size()/2; i++) {
            population.add(populationRank.get(i));
        }
    }

    public void setCrossoverRateForEveryPopulationSpecimen() {

        int populationSize = population.size();

    }
}
