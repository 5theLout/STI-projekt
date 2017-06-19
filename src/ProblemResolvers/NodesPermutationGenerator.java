package ProblemResolvers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-06-15.
 */
public class NodesPermutationGenerator {

    public static List<List<Node>> generatePerm(List<Node> original) {
        if (original.size() == 0) {
            List<List<Node>> result = new ArrayList<List<Node>>();
            result.add(new ArrayList<Node>());
            return result;
        }
        Node firstElement = original.remove(0);
        List<List<Node>> returnValue = new ArrayList<List<Node>>();
        List<List<Node>> permutations = generatePerm(original);
        for (List<Node> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<Node> temp = new ArrayList<Node>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

}
