package ProblemResolvers;

import Models.Position;

import java.util.List;

public class Algorithm {

    /* GA parameters */
    private static final double uniformRate = 0.5;
    public static double mutationRate = 0.5;
    private static final int tournamentSize = 6;
    private static final boolean elitism = true;

    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population pop, List<Node> availableGenesList) throws Exception {
        Population newPopulation = new Population(pop.size(), null);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newIndiv.repairIndividual(availableGenesList);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i), availableGenesList);
            newPopulation.getIndividual(i).repairIndividual(availableGenesList);
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        newSol.setStartNode(indiv1.getStartNode());
        // Loop through genes
        for (int i = 0; i < indiv1.getGoalNodes().size(); i++) {
            // Crossover
            if(indiv2.getGoalNodes().get(i) == null) {
                System.out.print("cdsnjk");
            }
            if(i < indiv1.getGoalNodes().size()/2) newSol.addGoalNode(new Node(new Position(indiv1.getGoalNodes().get(i).getPosition().getXPos(), indiv1.getGoalNodes().get(i).getPosition().getYPos())));
            else newSol.addGoalNode(new Node(new Position(indiv2.getGoalNodes().get(i).getPosition().getXPos(), indiv2.getGoalNodes().get(i).getPosition().getYPos())));
        }
        return newSol;
    }

    //// Mutate an individual
    private static void mutate(Individual indiv, List<Node> availableGenesList) {
        // Loop through genes
        for (int i = 0; i < indiv.getGoalNodes().size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                long indexToMutate =  Math.round(Math.random()*(indiv.getGoalNodes().size() - 1));
                long indexFromAvailableGenesList = Math.round(Math.random()*(availableGenesList.size() - 1));
                indiv.getGoalNodes().add((int) indexToMutate, availableGenesList.get((int)indexFromAvailableGenesList));
                indiv.getGoalNodes().remove((int) indexToMutate + 1);
            }
        }
    }

    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) throws Exception {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, null);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}