package ProblemResolvers;

import java.util.List;

/**
 * Created by Dominik on 2017-06-05.
 */
public class PopulationSpecimen {

    List<Node> nodes;

    Integer phenotype;

    Integer crossoverRate;

    public PopulationSpecimen(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Integer getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(Integer phenotype) {
        this.phenotype = phenotype;
    }

    public Integer getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(Integer crossoverRate) {
        this.crossoverRate = crossoverRate;
    }
}
