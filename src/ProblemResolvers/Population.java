package ProblemResolvers;

import Models.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-06-15.
 */
public class Population {

    Individual[] individuals;


    public Population(int populationSize, Individual[] individuals) {
        // Initialise population
        this.individuals = new Individual[populationSize];

        if (individuals != null) {
            Individual[] individualsCopy = new Individual[individuals.length];
            for(int i=0; i<individuals.length; i++) {
                individualsCopy[i] = new Individual(individuals[i].getGoalNodes(), individuals[i].getStartNode());
            }
            this.individuals = individualsCopy;
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() throws Exception {

        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        List<Node> nodes = new ArrayList<>();

        for(Node node : indiv.getGoalNodes()) {
            nodes.add(new Node(new Position(node.getPosition().getXPos(),node.getPosition().getYPos())));
        }
        Individual individualToSave = new Individual(nodes, indiv.getStartNode());
        individuals[index] = individualToSave;
    }
}